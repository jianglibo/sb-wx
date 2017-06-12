package com.jianglibo.wx.config;

import org.junit.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.jianglibo.wx.Tbase;

public class TestProtectedMethod extends Tbase {
	
	@Test(expected=AuthenticationCredentialsNotFoundException.class)
	public void t() {
		ProtectedMethod pm = (ProtectedMethod) context.getBeansOfType(Pmi.class).values().iterator().next();
		pm.m();
	}

}
