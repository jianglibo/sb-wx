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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.constant.UrlConstants;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.katharsis.exception.NotMultipartContentException;
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
	private FileItemProcessor fileItemProcessor;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			if (UrlConstants.UPLOAD_ENDPOINT.equals(request.getRequestURI())) {
				if (!SecurityUtil.hasRole(RoleNames.USER)) {
					fileItemProcessor.writeErrotToResponse(response, new AccessDeniedException("not login."));
					return;
				}
				if (ServletFileUpload.isMultipartContent(request)) {
					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload();
					// Parse the request
					try {
						FileItemIterator iter = upload.getItemIterator(request);
						String keyValue = null;
						List<Medium> media = new ArrayList<>();
						while (iter.hasNext()) {
						    FileItemStream item = iter.next();
						    String name = item.getFieldName();
						    if (item.isFormField()) {
						    	InputStream stream = item.openStream();
						    	if (keyName.equals(name)) {
						    		keyValue = Streams.asString(stream);
						    	}
						    } else {
						    	media.add(fileItemProcessor.processFileItem(item));
						    }
						}
						response.sendRedirect(getMediaRedirectUrl(media));
					} catch (FileUploadException e) {
						fileItemProcessor.writeErrotToResponse(response, e);
					}
				} else {
					fileItemProcessor.writeErrotToResponse(response, new NotMultipartContentException());
				}
				
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	private String getMediaRedirectUrl(List<Medium> media) {
		MyJsonApiUrlBuilder b = new MyJsonApiUrlBuilder("?");
		for(Medium m : media) {
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
}
