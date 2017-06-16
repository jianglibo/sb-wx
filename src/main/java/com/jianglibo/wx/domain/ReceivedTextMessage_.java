package com.jianglibo.wx.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-16T14:54:57.297+0800")
@StaticMetamodel(ReceivedTextMessage.class)
public class ReceivedTextMessage_ extends BaseEntity_ {
	public static volatile SingularAttribute<ReceivedTextMessage, String> toUserName;
	public static volatile SingularAttribute<ReceivedTextMessage, String> fromUserName;
	public static volatile SingularAttribute<ReceivedTextMessage, Long> createTime;
	public static volatile SingularAttribute<ReceivedTextMessage, String> content;
	public static volatile SingularAttribute<ReceivedTextMessage, String> msgType;
	public static volatile SingularAttribute<ReceivedTextMessage, String> msgId;
}
