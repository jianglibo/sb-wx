package com.jianglibo.wx;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.config.userdetail.BootUserDetailManager;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.repository.BootUserRepository;
import com.jianglibo.wx.repository.RoleRepository;
import com.jianglibo.wx.util.BootUserFactory;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.vo.BootUserAuthentication;
import com.jianglibo.wx.vo.BootUserPrincipal;
import com.jianglibo.wx.vo.RoleNames;

import io.katharsis.core.internal.boot.KatharsisBoot;

@Component
public class Tutil {
	
	public static String USER_1 = "user1";
	public static String USER_2 = "user2";
	
	public static String PASSWORD = "alUls82lsi^v";
	
    @Autowired
    protected RoleRepository roleRepo;
    
	@Autowired
	protected KatharsisBoot kboot;

    
	@Autowired
	@Qualifier("indentOm")
	protected ObjectMapper indentOm;

    @Autowired
    protected BootUserRepository bootUserRepo;
    
	@Autowired
	protected BootUserFactory bootUserFactory;

	@Autowired
	private BootUserDetailManager bootUserDetailManager;

	
	public BootUser createUser1() {
    	return createBootUser(USER_1, PASSWORD);
    }
    
	public BootUser createUser2() {
    	return createBootUser(USER_2, PASSWORD);
    }
	
	public BootUser loginAsAdmin() {
    	BootUser bu = createBootUser("admin",null,RoleNames.ROLE_ADMINISTRATOR);
        BootUserPrincipal pv = new BootUserPrincipal(bu);
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
        return bu;
    }
    
	public void loginAs(String name, String...rns) {
        BootUserPrincipal pv = new BootUserPrincipal(createBootUser(name,null,rns));
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
    }
	
    public BootUser createBootUser(String name,String password, String... rns) {
        List<Role> rnl = Stream.of(rns).map(Role::new).collect(Collectors.toList()); 

        if (!rnl.contains(new Role(RoleNames.USER))) {
            rnl.add(new Role(RoleNames.USER));
        }
        
        Set<Role> nroles = rnl.stream().map(rn -> {
        	Role r = roleRepo.findByName(rn.getName());
        	if (r == null) {
        		r = roleRepo.save(rn);
        	}
        	return r;
        }).collect(Collectors.toSet());
        
        if (password == null || password.trim().isEmpty()) {
        	password = "aA^" + new Random().nextDouble() + "0k";
        }

        BootUser p = bootUserRepo.findByName(name);
        
        if (p == null) {
        	BootUserPrincipal bp = new BootUserPrincipal(bootUserFactory.getBootUserBuilder(name, name + "m3958.com", name, name).enable().password(password).roles(nroles).build());
            p = bootUserDetailManager.createUserAndReturn(bp);
        } else {
        	p.setRoles(nroles);
        	p = bootUserRepo.save(p);
        }
        return p;
    }

	public ObjectMapper getObjectMapper() {
		return indentOm;
	}
	
}
