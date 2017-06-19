package com.jianglibo.wx;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.config.userdetail.BootUserDetailManager;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.repository.BootUserRepository;
import com.jianglibo.wx.repository.MediumRepository;
import com.jianglibo.wx.repository.PostRepository;
import com.jianglibo.wx.repository.RoleRepository;
import com.jianglibo.wx.util.BootUserFactory;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.vo.BootUserAuthentication;
import com.jianglibo.wx.vo.BootUserPrincipal;
import com.jianglibo.wx.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public abstract class Tbase extends M3958TsBase {

    protected MockMvc mvc;
    
	@Autowired
	protected TestRestTemplate restTemplate;
	
	@Autowired
	protected BootUserFactory bootUserFactory;
	
	@Autowired
	private BootUserDetailManager bootUserDetailManager;
    
    @Autowired
    protected TestUtil testUtil;

    @Autowired
    protected WebApplicationContext context;
    
    @Autowired
    protected ApplicationConfig applicationConfig;

    @Autowired
    protected BootUserRepository bootUserRepo;
    
    @Autowired
    protected MediumRepository mediumRepo;
    
    @Autowired
    protected PostRepository postRepo;


    @Autowired
    protected RoleRepository roleRepo;
    
    @Autowired
    protected ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public Pageable createApageable(int page, int size) {
        return new PageRequest(page, size);
    }

    public Pageable createApageable(int size) {
        return createApageable(0, size);
    }

    public Pageable createApageable() {
        return createApageable(10);
    }
    
    protected BootUser loginAsAdmin() {
    	BootUser bu = createBootUser("admin",null,RoleNames.ROLE_ADMINISTRATOR);
        BootUserPrincipal pv = new BootUserPrincipal(bu);
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
        return bu;
    }
    
    protected void loginAs(String name, String...rns) {
        BootUserPrincipal pv = new BootUserPrincipal(createBootUser(name,null,rns));
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
    }

    protected BootUser createBootUser(String name,String password, String... rns) {
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

    public String getRestUri(String uri) {
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        return getApiPrefix() + uri;
    }

    public String getApiPrefix() {
        return "/api/v1";
    }
}
