package com.jianglibo.wx.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-27T14:50:47.686+0800")
@StaticMetamodel(MessageNotify.class)
public class MessageNotify_ extends BaseEntity_ {
	public static volatile SingularAttribute<MessageNotify, BootUser> bootUser;
	public static volatile SingularAttribute<MessageNotify, String> ntype;
	public static volatile SingularAttribute<MessageNotify, Integer> number;
}
