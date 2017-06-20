package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.converter.MediumDtoConverter;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.util.UuidUtil;
import com.jianglibo.wx.vo.RoleNames;

/**
 * copy some code from @see {@link BasicAuthenticationFilter}
 * 
 * @author jianglibo@gmail.com
 *         2015年8月20日
 *
 */
@Component
@Priority(20)
public class FileUploadFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(FileUploadFilter.class);
	
	private static String keyName = "uploadSecret";
	
	@Autowired
	private MediumFacadeRepository mediumRepository;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	@Autowired
	private ApplicationConfig appConfig;
	
	private Path destFolder = null;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MediumDtoConverter mediumConverter;
	
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
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			if ("/fileupload".equals(request.getRequestURI())) {
				if (!SecurityUtil.hasRole(RoleNames.USER)) {
					writeUploadResult(response, new FileUploadResponse("accessDenied", "you should be authenticated."));
					return;
				}
				if (ServletFileUpload.isMultipartContent(request)) {
					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload();
					// Parse the request
					try {
				    	FileUploadResponse r = new FileUploadResponse("success", "");
						FileItemIterator iter = upload.getItemIterator(request);
						String keyValue = null;
						while (iter.hasNext()) {
						    FileItemStream item = iter.next();
						    String name = item.getFieldName();
						    InputStream stream = item.openStream();
						    if (item.isFormField()) {
						    	if (keyName.equals(name)) {
						    		keyValue = Streams.asString(stream);
						    	}
						    } else {
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
						    	r.getMedia().add(mediumConverter.entity2Dto(mi));
						    }
						}
//						if (appConfig.getUploadSecret().equals(keyValue)) {
//							writeUploadResult(response, r);
//						} else {
//							writeUploadResult(response, new FileUploadResponse("GuessWrong", ""));
//						}
						writeUploadResult(response, r);
				    	
					} catch (FileUploadException e) {
						writeUploadResult(response, new FileUploadResponse(FileUploadException.class.getSimpleName(), e.getMessage()));
					}
				} else {
					writeUploadResult(response, new FileUploadResponse("notMultipartContent", ""));
				}
				
			} else {
				chain.doFilter(request, response);
			}
		}
	}
	
	/**
	 * remove path part of file name.
	 * @param name
	 * @return
	 */
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

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
	
	public void writeUploadResult(HttpServletResponse response, FileUploadResponse furs) throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		objectMapper.writeValue(response.getWriter(), furs);
	}
	
	public static class FileUploadResponse {
		
		private String reason;
		private String description;
		
		private List<MediumDto> media = new ArrayList<>();
		
		public FileUploadResponse(){}
		
		public FileUploadResponse(String reason, String description) {
			super();
			this.reason = reason;
			this.description = description;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<MediumDto> getMedia() {
			return media;
		}
		public void setMedia(List<MediumDto> media) {
			this.media = media;
		}
	}
}
