package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "approve")
public class Approve extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8189805474312842749L;

	@ManyToOne
	private BootUser requester;
	
	@ManyToOne
	private BootUser receiver;
	
	private String targetType;
	
	private Long targetId;
	
	@Enumerated(EnumType.STRING)
	private ApproveState state = ApproveState.REQUEST_PENDING;
	
	private String forwhat;
	
	private String descriptionTpl;

	public BootUser getRequester() {
		return requester;
	}

	public void setRequester(BootUser requester) {
		this.requester = requester;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getDescriptionTpl() {
		return descriptionTpl;
	}

	public void setDescriptionTpl(String descriptionTpl) {
		this.descriptionTpl = descriptionTpl;
	}

	public BootUser getReceiver() {
		return receiver;
	}

	public void setReceiver(BootUser receiver) {
		this.receiver = receiver;
	}
	
	public String getForwhat() {
		return forwhat;
	}

	public void setForwhat(String forwhat) {
		this.forwhat = forwhat;
	}
	
	public ApproveState getState() {
		return state;
	}

	public void setState(ApproveState state) {
		this.state = state;
	}

	public static enum ApproveState {
		REQUEST_PENDING,REJECT,APPROVED, INVITE_PENDING
	}

	public static class ApproveBuilder<T extends BaseEntity> {
		private Approve approve = new Approve();
		
		public ApproveBuilder(T target) {
			approve.setTargetId(target.getId());
			approve.setTargetType(target.getClass().getName());
		}
		
		public ApproveBuilder<T> receiver(BootUser user) {
			approve.setReceiver(user);
			return this;
		}
		
		public ApproveBuilder<T> sender(BootUser user) {
			approve.setRequester(user);
			return this;
		}
		
		public Approve build() {
			return approve;
		}
	}
}
