package com.jianglibo.wx.config.userdetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import com.jianglibo.wx.domain.BootUser.Gender;
import com.jianglibo.wx.vo.BootUserPrincipal;

public class BootUserPrincipalBuilder {
	
	private String name;
	
	private String displayName;

	private String email;
	private String mobile;

	private boolean emailVerified;
	private boolean mobileVerified;

	private String password;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean disabled;
	
	private String avatar;
	
	private String openId;
	
    private String city;
    private String country;
    private String language;
    private String province;
    
    private Gender gender;
	
	private final BootUserManagerConfigurer<?> nextBuilder;

	BootUserPrincipalBuilder(BootUserManagerConfigurer<?> builder) {
		this.nextBuilder = builder;
	}

	public BootUserManagerConfigurer<?> and() {
		return nextBuilder;
	}

	public BootUserPrincipalBuilder name(String name) {
		Assert.notNull(name, "name cannot be null");
		this.name = name;
		return this;
	}

	public BootUserPrincipalBuilder email(String email) {
		Assert.notNull(displayName, "username cannot be null");
		this.email = email;
		return this;
	}

	public BootUserPrincipalBuilder emailVerified(boolean b) {
		this.emailVerified = b;
		return this;
	}
	
	public BootUserPrincipalBuilder avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public BootUserPrincipalBuilder mobileVerified(boolean b) {
		this.mobileVerified = b;
		return this;
	}

	public BootUserPrincipalBuilder mobile(String mobile) {
		Assert.notNull(displayName, "username cannot be null");
		this.mobile = mobile;
		return this;
	}

	public BootUserPrincipalBuilder displayName(String displayName) {
		Assert.notNull(displayName, "username cannot be null");
		this.displayName = displayName;
		return this;
	}
	
	public BootUserPrincipalBuilder gender(Gender gender) {
		this.gender = gender;
		return this;
	}


	public BootUserPrincipalBuilder password(String password) {
		Assert.notNull(password, "password cannot be null");
		this.password = password;
		return this;
	}
	
	public BootUserPrincipalBuilder openId(String openId) {
		this.openId = openId;
		return this;
	}

	public BootUserPrincipalBuilder roles(String... roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);
		for (String role : roles) {
			Assert.isTrue(!role.startsWith("ROLE_"), role + " cannot start with ROLE_ (it is automatically added)");
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return authorities(authorities);
	}

	public BootUserPrincipalBuilder authorities(GrantedAuthority... authorities) {
		return authorities(Arrays.asList(authorities));
	}

	private BootUserPrincipalBuilder authorities(List<? extends GrantedAuthority> authorities) {
		this.authorities = new ArrayList<GrantedAuthority>(authorities);
		return this;
	}

	public BootUserPrincipalBuilder authorities(String... authorities) {
		return authorities(AuthorityUtils.createAuthorityList(authorities));
	}

	public BootUserPrincipalBuilder accountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
		return this;
	}
	
	public BootUserPrincipalBuilder wxProperties(String city, String country, String language, String province) {
		this.city = city;
		this.country = country;
		this.language = language;
		this.province = province;
		return this;
	}

	public BootUserPrincipalBuilder accountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
		return this;
	}

	public BootUserPrincipalBuilder credentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
		return this;
	}

	public BootUserPrincipalBuilder disabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	public BootUserPrincipal build() {
		return new BootUserPrincipal(name, displayName, password, authorities,
				0L, email, mobile, avatar, gender, openId, emailVerified,
				mobileVerified, city, country, language, province, !disabled, !accountExpired, !credentialsExpired, !accountLocked);
		}
}
