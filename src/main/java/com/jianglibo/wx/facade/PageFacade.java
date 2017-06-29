package com.jianglibo.wx.facade;

public class PageFacade {

	private long offset;
	private long limit;
	private SortBroker[] sorts;
	
	public PageFacade(long limit) {
		this(0L, limit);
	}
	
	
	public PageFacade(long offset, long limit, SortBroker...sorts) {
		super();
		this.offset = offset;
		this.limit = limit;
		this.sorts = sorts;
	}
	
	
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public SortBroker[] getSorts() {
		return sorts;
	}
	public void setSorts(SortBroker[] sorts) {
		this.sorts = sorts;
	}
}
