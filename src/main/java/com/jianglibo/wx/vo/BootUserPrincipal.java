package com.jianglibo.wx.vo;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.BootUser.Gender;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.katharsis.dto.UserDto;

/**
 * @author jianglibo@gmail.com
 *
 */
public class BootUserPrincipal extends User {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String email;
    private final String mobile;
    private final String avatar;
    private final Gender gender;
    private final String openId;
    private final boolean emailVerified;
    private final boolean mobileVerified;
    
    private final String city;
    private final String country;
    private final String language;
    private final String province;

    public BootUserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Long id, String email, String mobile, String avatar, Gender gender, String openId, boolean emailVerified,
			boolean mobileVerified, String city, String country, String language, String province, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.avatar = avatar;
		this.gender = gender;
		this.openId = openId;
		this.emailVerified = emailVerified;
		this.mobileVerified = mobileVerified;
		this.city = city;
		this.country = country;
		this.language = language;
		this.province = province;
	}
    
    /**
     * there is no chance to build a principal with encoded password.
     * @param bu
     */
    public BootUserPrincipal(BootUser bu) {
    	super(bu.getDisplayName() == null ? bu.getName() : bu.getDisplayName(), bu.getPassword(), bu.isEnabled(), bu.isAccountNonExpired(), bu.isCredentialsNonExpired(), bu.isAccountNonLocked(), bu.getRoles());
		this.email = bu.getEmail();
		this.avatar = bu.getAvatar();
		this.mobile = bu.getMobile();
		this.gender = bu.getGender();
		this.openId = bu.getOpenId();
		this.emailVerified = bu.isEmailVerified();
		this.mobileVerified = bu.isMobileVerified();
		this.id = bu.getId();
		this.city = bu.getCity();
		this.country = bu.getCountry();
		this.language = bu.getLanguage();
		this.province = bu.getProvince();
    }
    
    public BootUserPrincipal(UserDto bu) {
    	super(bu.getDisplayName(),
    			bu.getPassword(),
    			bu.isEnabled(),
    			bu.isAccountNonExpired(),
    			bu.isCredentialsNonExpired(),
    			bu.isAccountNonLocked(),
    			bu.getRoles()
    			.stream().map(rdto -> new Role(rdto.getName())
    					).collect(Collectors.toList()));
		this.email = bu.getEmail();
		this.avatar = bu.getAvatar();
		this.mobile = bu.getMobile();
		this.gender = bu.getGender();
		this.openId = bu.getOpenId();
		this.emailVerified = bu.isEmailVerified();
		this.mobileVerified = bu.isMobileVerified();
		this.id = bu.getId();
		this.city = bu.getCity();
		this.country = bu.getCountry();
		this.language = bu.getLanguage();
		this.province = bu.getProvince();
    }

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getLanguage() {
		return language;
	}

	public String getProvince() {
		return province;
	}

	public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public Long getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public Gender getGender() {
        return gender;
    }
}
