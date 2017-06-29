package com.jianglibo.wx.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T09:02:14.507+0800")
@StaticMetamodel(BootGroup.class)
public class BootGroup_ extends BaseEntity_ {
	public static volatile SingularAttribute<BootGroup, String> name;
	public static volatile SingularAttribute<BootGroup, String> description;
	public static volatile SingularAttribute<BootGroup, Boolean> openToAll;
	public static volatile SingularAttribute<BootGroup, String> thumbUrl;
	public static volatile SingularAttribute<BootGroup, BootUser> creator;
	public static volatile ListAttribute<BootGroup, GroupUserRelation> members;
}
