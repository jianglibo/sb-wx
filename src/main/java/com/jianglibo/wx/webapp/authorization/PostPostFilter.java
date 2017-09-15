package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.facade.PageFacade;
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
	
	
	@Autowired
	private MediumFacadeRepository mediumRepo;
	
	@Autowired
	private PostFacadeRepository postRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	@Autowired
	private GroupUserRelationFacadeRepository guRepo;
	
	@Autowired
	private FileItemProcessor fileItemProcessor;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			if (UrlConstants.POSTFORM_ENDPOINT.equals(request.getRequestURI())) {
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
						List<Long> sharedUserIds = new ArrayList<>();
						
						List<Long> sharedGroupIds = new ArrayList<>();
						
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
						    		String commaStr = Streams.asString(stream);
						    		mediaIds = Stream.of(commaStr.split(",")).map(s -> Long.valueOf(s)).collect(Collectors.toList());
						    	} else if ("sharedUsers".equals(name)) {
						    		String commaStr = Streams.asString(stream);
						    		sharedUserIds = Stream.of(commaStr.split(",")).map(s -> Long.valueOf(s)).collect(Collectors.toList());
						    	} else if ("sharedGroups".equals(name)) {
						    		String commaStr = Streams.asString(stream);
						    		sharedGroupIds = Stream.of(commaStr.split(",")).map(s -> Long.valueOf(s)).collect(Collectors.toList());
						    	}
						    } else {
						    	media.add(fileItemProcessor.processFileItem(item));
						    }
						}
						Post post = new Post();
						post.setTitle(title);
						post.setContent(content);
						post.setCreator(userRepository.findOne(SecurityUtil.getLoginUserId(), false));
						post = postRepo.save(post, null);
						final Post finalPost = post;
						mediaIds.addAll(media.stream().map(dto -> dto.getId()).collect(Collectors.toList()));
						List<Medium> ms = mediaIds.stream().map(id -> {
							Medium m = mediumRepo.findOne(id, true);
//							m.setPost(finalPost);
							return mediumRepo.save(m, null);
							}).collect(Collectors.toList());
						post.setMedia(ms);
						postRepo.save(post,null);
						
						final Post p = post;
						sharedUserIds.stream()
							.forEach(uid -> postRepo.saveSharePost(p, userRepository.findOne(uid, true)));
						sharedGroupIds.stream()
							.map(gid -> groupRepo.findOne(gid, true))
							.flatMap(g -> guRepo.findByBootGroup(g, new PageFacade(10000L)).getContent().stream())
							.map(gur -> gur.getBootUser())
							.forEach(user -> postRepo.saveSharePost(p, user));
						
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
