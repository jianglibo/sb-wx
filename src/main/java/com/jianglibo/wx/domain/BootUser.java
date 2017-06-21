package com.jianglibo.wx.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    
    @OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.FEMALE;
    
    @OneToMany(mappedBy="bootUser", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<PostShare> postShares = new ArrayList<>();
    
    @OneToMany(mappedBy="bootUser", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<PostUnRead> postUnread = new ArrayList<>();
    
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
    
    @OneToMany(mappedBy="bootUser", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    private List<GroupUserRelation> bootGroups = new ArrayList<>();
    
    @OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
    private List<BootGroup> ownedGroups = new ArrayList<>();
    
    @OneToMany(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy="followed")
    private List<FollowRelation> followers = new ArrayList<>();
    
    @OneToMany(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy="follower")
    private List<FollowRelation> followeds = new ArrayList<>();
    
    @OneToMany(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy="post")
    private List<Medium> media = new ArrayList<>();
    
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

    public List<Medium> getMedia() {
		return media;
	}

	public void setMedia(List<Medium> media) {
		this.media = media;
	}

	public BootUser() {
    }

    
    /**
     * @param up
     * @param encodedPwd
     */
    public BootUser(BootUserPrincipal up, String encodedPwd) {
    	// 18 fields totally.
        setName(up.getUsername());
        setDisplayName(up.getDisplayName());
        setAvatar(up.getAvatar());
        setEmail(up.getEmail());
        setMobile(up.getMobile());
        setPassword(encodedPwd);
        setAccountNonExpired(up.isAccountNonExpired());
        setAccountNonLocked(up.isAccountNonLocked());
        setCredentialsNonExpired(up.isCredentialsNonExpired());
        setEnabled(up.isEnabled());
        setCreatedAt(new Date());
        setEmailVerified(up.isEmailVerified());
        setMobileVerified(up.isMobileVerified());
        setOpenId(up.getOpenId());
        setProvince(up.getProvince());
        setCity(up.getCity());
        setCountry(up.getCountry());
        setLanguage(up.getLanguage());
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<FollowRelation> getFollowers() {
		return followers;
	}

	public void setFollowers(List<FollowRelation> followers) {
		this.followers = followers;
	}

	public List<FollowRelation> getFolloweds() {
		return followeds;
	}

	public void setFolloweds(List<FollowRelation> followeds) {
		this.followeds = followeds;
	}

	public List<GroupUserRelation> getBootGroups() {
		return bootGroups;
	}

	public void setBootGroups(List<GroupUserRelation> bootGroups) {
		this.bootGroups = bootGroups;
	}

	public List<BootGroup> getOwnedGroups() {
		return ownedGroups;
	}

	public void setOwnedGroups(List<BootGroup> ownedGroups) {
		this.ownedGroups = ownedGroups;
	}

	public List<PostShare> getPostShares() {
		return postShares;
	}

	public void setPostShares(List<PostShare> postShares) {
		this.postShares = postShares;
	}

	public List<PostUnRead> getPostUnread() {
		return postUnread;
	}

	public void setPostUnread(List<PostUnRead> postUnread) {
		this.postUnread = postUnread;
	}



	public static enum Gender {
        MALE, FEMALE
    }
}
