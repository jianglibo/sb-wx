package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.katharsis.exception.NotMultipartContentException;
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
public class PostPostFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(PostPostFilter.class);
	
	private static String keyName = "uploadSecret";
	
	@Autowired
	private ApplicationConfig appConfig;
	
	private static String catchUrl = "/postpost";
	
	
	@Autowired
	private MediumFacadeRepository mediumRepo;
	
	@Autowired
	private PostFacadeRepository postRepo;
	
	@Autowired
	private FileItemProcessor fileItemProcessor;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			if (catchUrl.equals(request.getRequestURI())) {
				if (!SecurityUtil.hasRole(RoleNames.USER)) {
					fileItemProcessor.writeErrotToResponse(response, new AccessDeniedException("not login."));
					return;
				}
				if ("".equalsIgnoreCase(request.getMethod())) {
					
				}
				if (ServletFileUpload.isMultipartContent(request)) {
					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload();
					// Parse the request
					try {
						FileItemIterator iter = upload.getItemIterator(request);
						String title = null;
						String content = null;
						List<Long> mediaIds = new ArrayList<>();
						List<Medium> media = new ArrayList<>();
						while (iter.hasNext()) {
						    FileItemStream item = iter.next();
						    String name = item.getFieldName();
						    if (item.isFormField()) {
						    	InputStream stream = item.openStream();
						    	if ("title".equals(name)) {
						    		title = Streams.asString(stream);
						    	} else if ("content".equals(name)) {
						    		content = Streams.asString(stream);
						    	} else if ("media".equals(name)) {
						    		mediaIds.add(Long.valueOf(Streams.asString(stream)));
						    	}
						    } else {
						    	media.add(fileItemProcessor.processFileItem(item));
						    }
						}
						Post post = new Post();
						post.setTitle(title);
						post.setContent(content);
						post = postRepo.save(post);
						final Post finalPost = post;
						mediaIds.addAll(media.stream().map(dto -> dto.getId()).collect(Collectors.toList()));
						List<Medium> ms = mediaIds.stream().map(id -> {
							Medium m = mediumRepo.findOne(id);
							m.setPost(finalPost);
							return mediumRepo.save(m);
							}).collect(Collectors.toList());
						post.setMedia(ms);
						postRepo.save(post);
						response.sendRedirect(getPostRedirectUrl(post));
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
	

	private String getPostRedirectUrl(Post post) {
		return appConfig.getOutUrlResouceBase(JsonApiResourceNames.POST) + "/" + post.getId();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
	
}
