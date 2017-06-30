package com.jianglibo.wx.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-30T08:44:09.392+0800")
@StaticMetamodel(Post.class)
public class Post_ extends BaseEntity_ {
	public static volatile SingularAttribute<Post, String> title;
	public static volatile SingularAttribute<Post, String> content;
	public static volatile SingularAttribute<Post, BootUser> creator;
	public static volatile ListAttribute<Post, Medium> media;
	public static volatile ListAttribute<Post, PostShare> postShares;
	public static volatile SingularAttribute<Post, Boolean> toAll;
}
