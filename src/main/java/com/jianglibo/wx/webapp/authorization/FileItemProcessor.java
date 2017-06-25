package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.commons.fileupload.FileItemStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.util.UuidUtil;

@Component
public class FileItemProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(FileItemProcessor.class);
	
	@Autowired
	private ApplicationConfig appConfig;
	
	@Autowired
	private MediumFacadeRepository mediumRepository;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	private Path destFolder = null;
	
	@PostConstruct
	public void pc() {
		destFolder = Paths.get(appConfig.getUploadPath());
		if (!Files.exists(destFolder)) {
			try {
				Files.createDirectories(destFolder);
			} catch (IOException e) {
				logger.error("create upload destination folder failed. {}", destFolder.toAbsolutePath().normalize().toString());
			}
		}
	}

	public Medium processFileItem(FileItemStream item) throws IOException {
		InputStream stream = item.openStream();
		String originName = getOriginName(item.getName());
		Path destPath = getDestPath(destFolder, originName);
		Files.copy(stream, destPath);
		Medium mi = new Medium();
		mi.setLocalPath(destFolder.relativize(destPath).toString());
		mi.setSize(Files.size(destPath));
		mi.setContentType(item.getContentType());
		mi.setOrignName(originName);
		mi.setCreator(userRepository.findOne(SecurityUtil.getLoginUserId(), false));
		mi.setUrl(appConfig.getUploadLinkBase() + mi.getLocalPath());
		mi = mediumRepository.save(mi);
		return mi;
	}
	
	protected String getOriginName(String name) {
		return Paths.get(name).getFileName().toString();
	}


	protected static Path getDestPath(Path destFolder, String name) {
		int lastDot = name.lastIndexOf('.');
		String ext = "";
		if (lastDot != -1) {
			ext = name.substring(lastDot);
		}
		return destFolder.resolve(UuidUtil.uuidNoDash() + ext);
	}
	
}
