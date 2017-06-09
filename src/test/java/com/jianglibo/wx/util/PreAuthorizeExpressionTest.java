﻿package com.jianglibo.wx.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jianglibo.wx.vo.RoleNames;

public class PreAuthorizeExpressionTest {

	@Test
	public void t() {
		assertThat("hasAnyRole(" + "'" + RoleNames.ROLE_ADMINISTRATOR + "'" + ",'" + RoleNames.ROLE_SITEMANAGER + "')", equalTo("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_SITEMANAGER')"));
	}
}
