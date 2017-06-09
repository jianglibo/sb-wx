package com.jianglibo.wx.katharsis.dto;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.ReceivedTextMessage;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.RECEIVED_MESSAGE)
@DtoToEntity(entityClass=ReceivedTextMessage.class)
public class ReceivedMessageDto extends DtoBase<ReceivedMessageDto, ReceivedTextMessage>{
	
	private String toUserName;
	private String fromUserName;
	private long createTime;
	private String content;
	private String msgType;
	private long msgId;
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	@Override
	public ReceivedMessageDto fromEntity(ReceivedTextMessage entity) {
		return null;
	}
	@Override
	public ReceivedTextMessage patch(ReceivedTextMessage entity) {
		return null;
	}
}
