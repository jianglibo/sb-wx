package com.jianglibo.wx.config.userdetail;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsServiceConfigurer;


/**
 * 
 * @author jianglibo@gmail.com
 *
 * @param <B>
 */

public class BootUserManagerConfigurer<B extends ProviderManagerBuilder<B>> extends UserDetailsServiceConfigurer<B, BootUserManagerConfigurer<B>, BootUserDetailManager> {

	private final List<BootUserPrincipalBuilder> userBuilders = new ArrayList<BootUserPrincipalBuilder>();
	
	private Logger logger = LoggerFactory.getLogger(BootUserManagerConfigurer.class);
	
    public BootUserManagerConfigurer(BootUserDetailManager userDetailsManager) {
        super(userDetailsManager);
    }
    
	@Override
	protected void initUserDetailsService() throws Exception {
		for (BootUserPrincipalBuilder userBuilder : userBuilders) {
			try {
				getUserDetailsService().createUser(userBuilder.build());
			} catch (DataIntegrityViolationException e) {
				logger.info(e.getMessage());
			}
		}
	}

	public final BootUserPrincipalBuilder withUser(String name) {
		BootUserPrincipalBuilder userBuilder = new BootUserPrincipalBuilder(this);
		userBuilder.name(name);
		this.userBuilders.add(userBuilder);
		return userBuilder;
	}
}
