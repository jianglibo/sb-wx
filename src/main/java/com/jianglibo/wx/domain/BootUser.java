package com.jianglibo.wx.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jianglibo.wx.vo.BootUserPrincipal;

@Entity
@Table(name = "bootuser", uniqueConstraints = { @UniqueConstraint(columnNames = "name"), @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile"),@UniqueConstraint(columnNames = "openId") })
@Proxy(lazy=false)
public class BootUser extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String displayName;

    private String avatar;

    private boolean emailVerified;

    private boolean mobileVerified;
    
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.FEMALE;
    
    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotNull
    @Column(nullable = false)
    private String mobile;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @Column(nullable = false)
    @NotNull
    private String openId;
    
    private String city;
    
    private String country;
    
    private String language;
    
    private String province;
    
    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "BOOTUSER_ROLES")
    private Set<Role> roles = new HashSet<>();

    public BootUser() {
    }

    
    /**
     * @param bootUserPrincipal
     * @param encodedPwd
     */
    public BootUser(BootUserPrincipal bootUserPrincipal, String encodedPwd) {
        setName(bootUserPrincipal.getUsername());
        setDisplayName(bootUserPrincipal.getUsername());
        setAvatar(bootUserPrincipal.getAvatar());
        setEmail(bootUserPrincipal.getEmail());
        setMobile(bootUserPrincipal.getMobile());
        setPassword(encodedPwd);
        setAccountNonExpired(bootUserPrincipal.isAccountNonExpired());
        setAccountNonLocked(bootUserPrincipal.isAccountNonLocked());
        setCredentialsNonExpired(bootUserPrincipal.isCredentialsNonExpired());
        setEnabled(bootUserPrincipal.isEnabled());
        setCreatedAt(new Date());
        setEmailVerified(bootUserPrincipal.isEmailVerified());
        setMobileVerified(bootUserPrincipal.isMobileVerified());
    }

    @PrePersist
    public void beforePersist() {
        setOpenId(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

	public static enum Gender {
        MALE, FEMALE
    }
}
