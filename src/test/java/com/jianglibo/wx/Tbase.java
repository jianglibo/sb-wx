package com.jianglibo.wx;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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
import com.jianglibo.wx.repository.BootGroupRepository;
import com.jianglibo.wx.repository.BootUserRepository;
import com.jianglibo.wx.repository.MediumRepository;
import com.jianglibo.wx.repository.PostRepository;
import com.jianglibo.wx.repository.RoleRepository;
import com.jianglibo.wx.util.BootUserFactory;

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
	protected BootGroupRepository groupRepo;

    @Autowired
    protected RoleRepository roleRepo;
    
    @Autowired
    protected ObjectMapper objectMapper;
    
    @Autowired
    protected Tutil tutil;

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
