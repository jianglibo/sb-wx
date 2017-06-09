﻿package com.jianglibo.wx.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.jwt.JwtUtil;

@Component
public class JsonApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if (authException instanceof InsufficientAuthenticationException) {
			jwtUtil.writeForbidenResponse(response, "Full authentication is required to access this resource");
		} else {
			jwtUtil.writeForbidenResponse(response, "");
		}
	}

}
