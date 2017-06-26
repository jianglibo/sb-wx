package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.constant.AppErrorCodes;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.util.UuidUtil;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;

@Component
public class FileItemProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(FileItemProcessor.class);
	
	@Autowired
	private ApplicationConfig appConfig;
	
	@Autowired
	private MediumFacadeRepository mediumRepository;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
	
	public ErrorResponse toErrorResponse(Exception e, String errorCode) {
		ErrorData ed = ErrorData
				.builder()
				.setTitle(AccessDeniedException.class.getName())
				.setCode(errorCode)
				.setDetail(e.getMessage())
				.build();
		return ErrorResponse.builder().setStatus(HttpStatus.BAD_REQUEST.value())
		.setSingleErrorData(ed).build();
	}
	

	public void writeErrotToResponse(HttpServletResponse response, Exception exp) throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		String ec;
		if (exp instanceof AccessDeniedException) {
			ec = AppErrorCodes.ACCESS_DENIED;
		} else if (exp instanceof FileUploadException) {
			ec = AppErrorCodes.FILE_UPLOAD;
		} else {
			ec = AppErrorCodes.UNKNOWN;
		}
		objectMapper.writeValue(response.getWriter(), toErrorResponse(exp, ec));
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
