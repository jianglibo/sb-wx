package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BaseEntity;

public class Page<T extends BaseEntity> {
	private long totalResourceCount;
	private List<T> content;
	
	public Page(long totalResourceCount, List<T> content) {
		super();
		this.totalResourceCount = totalResourceCount;
		this.content = content;
	}
	public long getTotalResourceCount() {
		return totalResourceCount;
	}
	public void setTotalResourceCount(long totalResourceCount) {
		this.totalResourceCount = totalResourceCount;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
}
