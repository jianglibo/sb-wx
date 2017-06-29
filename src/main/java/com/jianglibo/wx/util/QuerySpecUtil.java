package com.jianglibo.wx.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.facade.SortBroker;

import io.katharsis.queryspec.Direction;
import io.katharsis.queryspec.FilterOperator;
import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;

/**
 * If only there is a way to translate QuerySpec to the final execution code.
 * By now, take the manually parse solution.  
 *  
 * @author jianglibo@hotmail.com
 *
 */

public class QuerySpecUtil {
	
	public static class RelationQuery {
		private String relationName;
		private List<Long> relationIds;
		
		public RelationQuery(String relationName, List<Long> relationIds) {
			setRelationName(relationName);
			setRelationIds(relationIds);
		}
		
		public String getRelationName() {
			return relationName;
		}
		public void setRelationName(String relationName) {
			this.relationName = relationName;
		}
		public List<Long> getRelationIds() {
			return relationIds;
		}
		public void setRelationIds(List<Long> relationIds) {
			this.relationIds = relationIds;
		}
	}

	public static List<Long> hasMyId(QuerySpec spec) {
		List<Long> ids = new ArrayList<>();
		Optional<FilterSpec> fs =  spec.getFilters().stream().filter(f -> f.getOperator() == FilterOperator.EQ && f.getAttributePath().size() == 1 && "id".equals(f.getAttributePath().get(0))).findAny();
		if (fs.isPresent()) {
			Object v = fs.get().getValue();
			if (v instanceof Collection) {
				ids.addAll((Collection<? extends Long>) v);
			} else {
				ids.add((Long) v);
			}
		}
		return ids;
	}
	
	public static RelationQuery findRelationQuery(QuerySpec spec) {
		List<Long> ids = new ArrayList<>();
		Optional<FilterSpec> fs =  spec.getFilters().stream().filter(f -> f.getOperator() == FilterOperator.EQ && f.getAttributePath().size() > 1 && "id".equals(f.getAttributePath().get(f.getAttributePath().size() - 1))).findAny();
		if (fs.isPresent()) {
			return new RelationQuery(fs.get().getAttributePath().get(0), fs.get().getValue() instanceof Iterable ? (List<Long>)fs.get().getValue() : Arrays.asList((Long)fs.get().getValue()));
		} else {
			return null;
		}
	}
	
	public static String[] getSortFields(QuerySpec spec) {
		return spec.getSort().stream().map(sort -> {
			String f = sort.getAttributePath().stream().collect(Collectors.joining(","));
			return sort.getDirection() == Direction.ASC ? f : "-" + f;
		}).toArray(String[]::new);
	}
	
	public static SortBroker[] getSortBrokers(QuerySpec spec) {
		return spec.getSort().stream().map(sort -> {
			String f = sort.getAttributePath().stream().collect(Collectors.joining(","));
			return sort.getDirection() == Direction.ASC ? new SortBroker(f, true) : new SortBroker(f, false);
		}).toArray(f -> new SortBroker[f]);
	}
	
	public static PageFacade getPageFacade(QuerySpec spec) {
		return new PageFacade(spec.getOffset(), spec.getLimit(), getSortBrokers(spec));
	}
	
	public static Optional<String> getFilterStringValue(QuerySpec querySpec, String fn) {
		Optional<FilterSpec> ofs = querySpec.getFilters().stream().filter(f -> fn.equals(f.getAttributePath().get(0))).findAny();
		if (ofs.isPresent()) {
			Object v = ofs.get().getValue();
			if (v == null) {
				return Optional.empty();
			}
			if (v instanceof String) {
				if (((String) v).trim().isEmpty()) {
					return Optional.empty();
				} else {
					return Optional.of(((String) v).trim());
				}
			} else {
				throw new RuntimeException(String.format("filter type is not implementation.", v.getClass().getName()));
			}
		} else {
			return Optional.empty();
		}
	}
}
