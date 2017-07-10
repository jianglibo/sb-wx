package com.jianglibo.wx.constant;

public class PreAuthorizeExpression {
	
	public static final String HAS_ADMINISTRATOR_ROLE = "hasRole('ADMINISTRATOR')";
	
	public static final String IS_FULLY_AUTHENTICATED = "isFullyAuthenticated()";
	
	public static final String IS_REMEMBER_ME = "isRememberMe()";
	
	public static final String IS_ANONYMOUS = "isAnonymous()";
	
	public static final String PERMIT_ALL = "permitAll";
	
	public static final String DENY_ALL = "denyAll";
	
	public static final String ENTITY_ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE = "hasRole('ADMINISTRATOR') or (#entity.id == principal.id)";
	
	public static final String ENTITY_CREATOR_ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE = "hasRole('ADMINISTRATOR') or (#entity.creator.id == principal.id)";
	
	public static final String RETURN_OBJECT_EQ = "returnObject";
	
	public static final String PRINCIPAL_ID = "principal.id";

}
