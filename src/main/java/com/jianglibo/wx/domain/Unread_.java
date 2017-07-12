package com.jianglibo.wx.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-12T08:55:28.690+0800")
@StaticMetamodel(Unread.class)
public class Unread_ extends BaseEntity_ {
	public static volatile SingularAttribute<Unread, Long> obid;
	public static volatile SingularAttribute<Unread, String> type;
	public static volatile SingularAttribute<Unread, BootUser> bootUser;
}
