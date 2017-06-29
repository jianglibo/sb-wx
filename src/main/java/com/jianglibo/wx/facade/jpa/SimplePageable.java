package com.jianglibo.wx.facade.jpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.facade.SortBroker;

import io.katharsis.queryspec.Direction;
import io.katharsis.queryspec.QuerySpec;

public class SimplePageable implements Pageable {
	
	private final Sort sort;
	private final int perPage;
	private final int curPage;
	
	private final int offset;
	
	public SimplePageable(PageFacade pf) {
		this(pf.getOffset(), pf.getLimit(), pf.getSorts());
	}
	
	public SimplePageable(long offset, long limit,SortBroker...filterFields) {
		this.offset = (int) offset;
		this.perPage = (int) limit;
		this.curPage = (int) Math.ceil(offset / limit);
		if (filterFields.length == 0) {
			this.sort = null;
		} else {
			List<Order> orders = Stream.of(filterFields).map(field -> {
				if (field.isAscending()) {
					return new Order(Sort.Direction.ASC, field.getFieldName());
				} else {
					return new Order(Sort.Direction.DESC, field.getFieldName());
				}
			}).collect(Collectors.toList());
			this.sort = new Sort(orders);
		}
	}
	
	public SimplePageable(long offset, long limit,String...filterFields) {
		this.offset = (int) offset;
		this.perPage = (int) limit;
		this.curPage = (int) Math.ceil(offset / limit);
		if (filterFields.length == 0) {
			this.sort = null;
		} else {
			List<Order> orders = Stream.of(filterFields).map(field -> {
				if (field.startsWith("-")) {
					return new Order(Sort.Direction.DESC, field);
				} else {
					return new Order(Sort.Direction.ASC, field);
				}
			}).collect(Collectors.toList());
			this.sort = new Sort(orders);
		}
	}
	
	public SimplePageable(QuerySpec querySpec) {
		if (querySpec.getLimit() == null) {
			this.perPage = 0;
			this.offset = (int)querySpec.getOffset();
			this.curPage = 0;
		} else {
			this.perPage = querySpec.getLimit().intValue();
			this.offset = (int)querySpec.getOffset();
			this.curPage = (int) Math.ceil(querySpec.getOffset() / querySpec.getLimit());
		}
		List<Order> orders = querySpec.getSort().stream().map(sc -> {
			return new Order(sc.getDirection() == Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, sc.getAttributePath().get(0));
		}).collect(Collectors.toList());
		if (!orders.isEmpty()) {
			sort = new Sort(orders);
		} else {
			sort = null;
		}
	}

	@Override
	public int getPageNumber() {
		return curPage;
	}

	@Override
	public int getPageSize() {
		return perPage;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

}
