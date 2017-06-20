package com.jianglibo.wx.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-20T09:10:15.278+0800")
@StaticMetamodel(FollowRelation.class)
public class FollowRelation_ extends BaseEntity_ {
	public static volatile SingularAttribute<FollowRelation, BootUser> befollowed;
	public static volatile SingularAttribute<FollowRelation, BootUser> follower;
}
