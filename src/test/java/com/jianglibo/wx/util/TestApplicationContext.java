package com.jianglibo.wx.util;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;

import com.jianglibo.wx.Tbase;
import com.jianglibo.wx.config.DtoEntityMapper;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.repository.SimpleJpaRepositoryBase;
import com.jianglibo.wx.repository.RepositoryBase;
import com.jianglibo.wx.repository.RoleRepository;

public class TestApplicationContext extends Tbase {
	
	@Autowired
	private Repositories repositories;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	
	@Test
	public void tByname() {
		RoleRepository rr = (RoleRepository) repositories.getRepositoryFor(Role.class);
		long n = rr.findAll().size();
		assertThat(n, greaterThan(0L));
	}

	@Test
	public void t() {
		Map<String, RepositoryBase> rbs = context.getBeansOfType(RepositoryBase.class);
		Map<String, SimpleJpaRepositoryBase> dsrs = context.getBeansOfType(SimpleJpaRepositoryBase.class);
		assertThat(rbs.size() / 2, equalTo(dsrs.size()));
		
		Set<String> keys = dsrs.keySet();
		
		for(String bn : keys) {
			assertThat(rbs.get(bn), equalTo(dsrs.get(bn)));
		}
	}
}
