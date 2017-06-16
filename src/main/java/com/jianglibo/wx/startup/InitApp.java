package com.jianglibo.wx.startup;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.facade.RoleFacadeRepository;
import com.jianglibo.wx.vo.RoleNames;

@Component
public class InitApp {

	@Autowired
	private RoleFacadeRepository roleRepo;
	
	@PostConstruct
	public void pc() {
		Set<String> rnIndb = roleRepo.findAll().stream().map(role -> role.getName()).collect(Collectors.toSet());
		RoleNames.allFields().stream().filter(n -> !rnIndb.contains(n)).map(rn -> new Role(rn)).forEach(r -> {
			roleRepo.initSave(r);
		});
	}
}
