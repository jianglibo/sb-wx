package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.vo.BootUserAuthentication;
import com.jianglibo.wx.vo.BootUserPrincipal;
import com.jianglibo.wx.vo.RoleNames;

/**
 * copy some code from @see {@link BasicAuthenticationFilter}
 * Maybe it's no need to match pattern. Check every income request, authenticate if jwt header exists. 
 * @author jianglibo@gmail.com
 *         2015年8月20日
 *
 */
@Component
@Priority(19)
public class WxSessionFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(WxSessionFilter.class);
	
	@Autowired
	private WxLoginService wxLoginService;
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// already authenticated.
		if (SecurityUtil.hasRole(RoleNames.USER)) {
			chain.doFilter(req, res);
			return;
		}
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			try {
				invokeSessionVerify(request, response);
				chain.doFilter(req, res);
			} catch (AuthenticationException | AccessDeniedException | WxAuthorizationAPIException e) {
	            SecurityContextHolder.clearContext();
	            throw new AccessDeniedException("authentication exception.");
			}
//			if (HttpMethod.POST.matches(request.getMethod()) && JwtBasicFilter.negPathPattern.matcher(request.getRequestURI()).matches()) {
//				chain.doFilter(request, response);
//			} else if (JwtBasicFilter.pathPattern.matcher(request.getRequestURI()).matches() || UrlConstants.UPLOAD_ENDPOINT.equals(request.getRequestURI()) || UrlConstants.POSTFORM_ENDPOINT.equals(request.getRequestURI())) {
//				try {
//					invokeSessionVerify(request, response);
//					chain.doFilter(req, res);
//				} catch (AuthenticationException | AccessDeniedException | WxAuthorizationAPIException e) {
//		            SecurityContextHolder.clearContext();
//		            throw new AccessDeniedException("authentication exception.");
//				}
//			} else {
//				chain.doFilter(request, response);
//			}
		}
	}

    private void invokeSessionVerify(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException, IOException, WxAuthorizationAPIException {
		String id = request.getHeader(WxConstants.WX_HEADER_ID);
		String skey = request.getHeader(WxConstants.WX_HEADER_SKEY);
		if (id != null && skey != null) {
			WxUserInfo userInfo = wxLoginService.check(id, skey);
			BootUser bu = userRepo.findByOpenId(userInfo.getOpenId());
			if (bu != null) {
		        BootUserPrincipal principal = new BootUserPrincipal(bu);
		        BootUserAuthentication buan = new BootUserAuthentication(principal);
		        SecurityContextHolder.getContext().setAuthentication(buan);
			} else {
				logger.error("had once login but not saved wx user: {}", userInfo.getOpenId());
				throw new AccessDeniedException(String.format("had once login but not saved wx user: {}", userInfo));
			}
		}
//		else {
//			throw new AccessDeniedException("no weixin id and skey header.");
//		}
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
