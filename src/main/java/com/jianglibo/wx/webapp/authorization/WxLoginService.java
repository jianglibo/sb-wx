package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.config.userdetail.BootUserDetailManager;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.util.BootUserFactory;
import com.jianglibo.wx.vo.BootUserPrincipal;
import com.jianglibo.wx.vo.RoleNames;
import com.jianglibo.wx.webapp.WxStateConfiguration;

//https://github.com/tencentyun/wafer/wiki/%E4%BC%9A%E8%AF%9D%E6%9C%8D%E5%8A%A1

@Component
public class WxLoginService {

	private static Logger logger = LoggerFactory.getLogger(WxLoginService.class);

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private BootUserFacadeRepository bootUserRepository;
	
	@Autowired
	private BootUserDetailManager bootUserDetailManager;

	@Autowired
	private WxStateConfiguration wxStateConfiguration;
	
	@Autowired
	private BootUserFactory bootUserFactory;

	public LoginResponseToPhone login(String code, String encryptedData, String iv) throws WxAuthorizationAPIException {
		SessionServerPostBody sab = SessionServerBodyBuilderFactory.getIdskeyBodyBuilder()
				.withCode(code)
				.withEncryptData(encryptedData)
				.withIv(iv)
				.build();

		// lead to "no suitable HttpMessageConverter found for response type
		// exception".
		// a possible cause is server side return incorrect media type, for
		// instance, text/html.
		SessionServerResponseBody body = postForSessionServerResponseBody(sab);
		WxUserInfo userInfo = body.getReturnData().getUser_info();
		
		if (bootUserRepository.findByOpenId(userInfo.getOpenId()) == null) { // for first time login user. create new user.
			saveFirstLoginUser(userInfo);
		}
		
		LoginResponseToPhone lr = new LoginResponseToPhone();
		lr.setId(body.getReturnData().getId());
		lr.setSkey(body.getReturnData().getSkey());
		return lr;
	}

	/**
	 * userInfo lack of name, email,mobile informations, need a trick to keep them unique. combine openid with theses properties.
	 * 
	 * @param userInfo
	 */
	private void saveFirstLoginUser(WxUserInfo userInfo) {
		BootUser bu = bootUserFactory.getBootUserBuilder(
				userInfo.getOpenId(), 
				userInfo.getOpenId() + "@.jianglibo.com",
				userInfo.getOpenId(), 
				userInfo.getOpenId())
			.avatar(userInfo.getAvatarUrl())
			.displayName(userInfo.getNickName())
			.gender(userInfo.getGender())
			.password(String.valueOf(new Random().nextDouble()))
			.withWxProperties(userInfo.getCity(), userInfo.getCountry(), userInfo.getLanguage(), userInfo.getProvince())
			.roles(RoleNames.MINIAPP_USER)
			.build();
		BootUserPrincipal bup = new BootUserPrincipal(bu);
		bootUserDetailManager.createUser(bup);
	}

	public WxUserInfo check(String id, String skey) throws WxAuthorizationAPIException {
		SessionServerPostBody sab = SessionServerBodyBuilderFactory.getAuthBodyBuilder().withId(id).withSkey(skey)
				.build();
		return postForSessionServerResponseBody(sab).getReturnData().getUser_info();
	}

	private SessionServerResponseBody postForSessionServerResponseBody(SessionServerPostBody postBody)
			throws WxAuthorizationAPIException {
		ResponseEntity<String> response = null;
		SessionServerResponseBody body = null;
		String postBodyString = null;
		String responseString = null;
		StringBuffer exmessage = new StringBuffer();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			postBodyString = objectMapper.writeValueAsString(postBody);
			exmessage.append(postBodyString);
			HttpEntity<String> requestEntity = new HttpEntity<String>(postBodyString, headers);
			response = restTemplate.exchange(
					wxStateConfiguration.getAuthServerUrl(),
					HttpMethod.POST,
					requestEntity,
					String.class);
			response.getHeaders().forEach((k, l) -> {
				if (l.size() > 0) {
					exmessage.append("\n").append(k).append(": ").append(l.get(0));
				}
			});
			exmessage.append("\n").append("status: ").append(response.getStatusCode().value());
			responseString = response.getBody();
			logger.error("----: {}", responseString);
			exmessage.append(responseString);
			body = objectMapper.readValue(responseString, SessionServerResponseBody.class);
			if (body.getReturnCode() != 0) {
				logger.error("[[[return code not eq 0]]]: {}, {}", body.getReturnCode(), body.getReturnMessage());
				throw new WxAuthorizationAPIException(responseString, null);
			}
			return body;
		} catch (RestClientException | IOException e) {
			logger.error("[[[throw exception]]]: {}", e.getMessage());
			logger.error(responseString);
			throw new WxAuthorizationAPIException(exmessage.append(e.toString()).toString(), e);
		}
	}
}
