package com.jianglibo.wx.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.BootUser.Gender;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.BOOT_USER)
@DtoToEntity(entityClass=BootUser.class)
public class UserDto extends DtoBase<UserDto, BootUser> {
	
	public static interface OnCreateGroup {}
	
    private String displayName;
    private String avatar;
    private boolean emailVerified;
    private boolean mobileVerified;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private String openId;
    private boolean enabled;
    private Gender gender;
    private String city;
    private String country;
    private String language;
    private String province;
    
    @JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="creator")
    private List<PostDto> posts;
    
    @JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="befollowed")
    private List<FollowRelationDto> follow2me;
    
    @JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite = "follower")
    private List<FollowRelationDto> ifollow2;
    
    @JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="creator")
    private List<MediumDto> media;
    
    
    public List<PostDto> getPosts() {
		return posts;
	}

	public void setPosts(List<PostDto> posts) {
		this.posts = posts;
	}



	public List<FollowRelationDto> getFollow2me() {
		return follow2me;
	}

	public void setFollow2me(List<FollowRelationDto> follow2me) {
		this.follow2me = follow2me;
	}

	public List<FollowRelationDto> getIfollow2() {
		return ifollow2;
	}

	public void setIfollow2(List<FollowRelationDto> ifollow2) {
		this.ifollow2 = ifollow2;
	}

	public List<MediumDto> getMedia() {
		return media;
	}

	public void setMedia(List<MediumDto> media) {
		this.media = media;
	}

	@NotNull
    @Size(min=6, max=36)
    private String name;

    @NotNull
    @Size(min=6, max=36)
    @Email
    private String email;

    @NotNull
    @Size(min=6, max=36, groups=OnCreateGroup.class)
    private String password;

    @NotNull
    @Size(min=8, max=16)
    private String mobile;

    
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

    private List<RoleDto> roles = new ArrayList<>();
    
    public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

//	@Override
//	public BootUser patch(BootUser entity) {
//		entity.setAccountNonExpired(isAccountNonExpired());
//		entity.setAccountNonLocked(isAccountNonLocked());
//		entity.setCredentialsNonExpired(isCredentialsNonExpired());
//		entity.setEmailVerified(isEmailVerified());
//		entity.setMobileVerified(isMobileVerified());
//		entity.setEnabled(isEnabled());
//		
//		entity.setAvatar(getAvatar());
//		entity.setDisplayName(getDisplayName());
//		entity.setEmail(getEmail());
//		entity.setGender(getGender());
//		entity.setId(getId());
//		entity.setMobile(getMobile());
//		entity.setName(getName());
//		return entity;
//	}
//	
//	public BootUser patchLeaveStatusUnChanged(BootUser entity) {
//		entity.setAvatar(getAvatar());
//		entity.setDisplayName(getDisplayName());
//		entity.setEmail(getEmail());
//		entity.setGender(getGender());
//		entity.setId(getId());
//		entity.setMobile(getMobile());
//		entity.setName(getName());
//		return entity;
//	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
