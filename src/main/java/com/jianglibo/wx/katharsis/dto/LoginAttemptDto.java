package com.jianglibo.wx.katharsis.dto;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.domain.ThirdPartLogin.Provider;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.LOGIN_ATTEMPT)
@DtoToEntity(entityClass=LoginAttempt.class)
public class LoginAttemptDto extends  DtoBase<LoginAttemptDto, LoginAttempt>{
	
    private String username;
    
    private String password;

	private String remoteAddress;
	
	private String sessionId;
	
	private String jwtToken;
	
	private UserDto user;
	
	private Provider provider = Provider.NORMAL;
	
	private boolean success;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
