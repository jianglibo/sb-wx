package com.jianglibo.wx.domain;

import com.jianglibo.wx.eu.ApproveState;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T17:37:57.061+0800")
@StaticMetamodel(Approve.class)
public class Approve_ extends BaseEntity_ {
	public static volatile SingularAttribute<Approve, BootUser> requester;
	public static volatile SingularAttribute<Approve, BootUser> receiver;
	public static volatile SingularAttribute<Approve, String> targetType;
	public static volatile SingularAttribute<Approve, Long> targetId;
	public static volatile SingularAttribute<Approve, ApproveState> state;
	public static volatile SingularAttribute<Approve, String> forwhat;
	public static volatile SingularAttribute<Approve, String> descriptionTpl;
}
