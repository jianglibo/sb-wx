package com.jianglibo.wx.api;

import java.time.Instant;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jianglibo.wx.config.ApplicationConfig;

@Component
public class AccessTokenHolder {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessTokenHolder.class);
	
	private static long gap = 5 * 60; // 10 minutes.
	
	private static int retryTimes = 3;
	
	private static final long checkRate = 1000 * 60; // one minute.

	private String tpl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	private String url;
	private AccessTokenObject ato;
	
	@Autowired
	private ApplicationConfig appConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private long nextRefreshTime;
	
	@PostConstruct
	public void pc() {
		this.url = String.format(tpl, appConfig.getAppId(), appConfig.getAppSecret());
		try {
			ato = restTemplate.getForObject(url, AccessTokenObject.class);
			nextRefreshTime = Instant.now().getEpochSecond() + ato.getExpires_in();
		} catch (RestClientException e) {
			logger.error("---get wx token failed--- {}", e.getMessage());
		}
	}
	
	public synchronized String getAccessToken() {
		if (ato == null) {
			ato = restTemplate.getForObject(url, AccessTokenObject.class);
			nextRefreshTime = Instant.now().getEpochSecond() + ato.getExpires_in();
		}
		return this.ato.getAccess_token();
	}
	
//	@Scheduled(initialDelay=100000, fixedRate=AccessTokenHolder.checkRate)
	public void refreshToken() {
		if (nextRefreshTime - Instant.now().getEpochSecond() < gap) {
			for(int i = 0;i<retryTimes;i++) {
				AccessTokenObject nato = restTemplate.getForObject(url, AccessTokenObject.class);
				if (nato.getAccess_token() != null) {
					ato = nato;
					break;
				} else {
					logger.error("refresh token failed. errorcode: {}, errmsg: {}", nato.getErrcode(), nato.getErrmsg());
				}
			}
		}
	}
	
	public static class AccessTokenObject {
		private String access_token;
		private int expires_in;
		private String errcode;
		private String errmsg;
		
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public int getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		public String getErrcode() {
			return errcode;
		}
		public void setErrcode(String errcode) {
			this.errcode = errcode;
		}
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
	}
	
}
