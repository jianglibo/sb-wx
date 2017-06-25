package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.MediumDtoConverter;
import com.jianglibo.wx.util.MyJsonApiUrlBuilder;
import com.jianglibo.wx.util.SecurityUtil;
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
	private ApplicationConfig appConfig;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MediumDtoConverter mediumConverter;
	
	@Autowired
	private FileItemProcessor fileItemProcessor;
	
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
						List<MediumDto> mediumDtos = new ArrayList<>();
						while (iter.hasNext()) {
						    FileItemStream item = iter.next();
						    String name = item.getFieldName();
						    if (item.isFormField()) {
						    	InputStream stream = item.openStream();
						    	if (keyName.equals(name)) {
						    		keyValue = Streams.asString(stream);
						    	}
						    } else {
						    	Medium mi = fileItemProcessor.processFileItem(item);
						    	mediumDtos.add(mediumConverter.entity2Dto(mi, Scenario.NEW));
						    }
						}
//						r.setMedia(mediumDtos);
//						if (appConfig.getUploadSecret().equals(keyValue)) {
//							writeUploadResult(response, r);
//						} else {
//							writeUploadResult(response, new FileUploadResponse("GuessWrong", ""));
//						}
						response.sendRedirect(getMediaRedirectUrl(mediumDtos));
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

	private String getMediaRedirectUrl(List<MediumDto> mediumDtos) {
		MyJsonApiUrlBuilder b = new MyJsonApiUrlBuilder("?");
		for(MediumDto m : mediumDtos) {
			b.filters("id", m.getId());
		}
		String url = b.build();
		return appConfig.getOutUrlResouceBase(JsonApiResourceNames.MEDIUM) + url;
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
	}
}
