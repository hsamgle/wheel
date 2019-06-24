package com.hsamgle.mongodb.entity.aggregate;


import com.hsamgle.mongodb.annatation.MFuture;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.Group;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mongodb.morphia.aggregation.Group.*;

/**
 *
 *  @feture   :	    TODO		    构建数据聚合条件
 *	@file_name:	    Groups.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:36
 *	@company:		江南皮革厂
 */
public final class Groups extends AggregateOptions {

	private List<Group> ids;

	private List<Group> groups;

	public Groups() {
		ids = new ArrayList<>();
		groups = new ArrayList<>();
		super.setType(Type.GROUP);
	}

	public List<Group> getIds() {
		return ids;
	}

	public List<Group> getGroups() {
		return groups==null?new ArrayList<>():groups;
	}

	public Groups id(String field){
		if(!StringUtils.isEmpty(field)){
			this.ids.add(Group.grouping(field));
		}
		this.groups.add(Group.grouping(field,first(field)));
		return this;
	}

	public Groups id(Param param){
		String source = param.source;
		String property = param.property;
		property=StringUtils.isEmpty(property)?source:property;
		if(param.future!=null){
			this.ids.add(Group.grouping(property,buildAccumulator(param)));
		}else{
			this.ids.add(Group.grouping(property,source));
		}
		this.groups.add(Group.grouping(property,first(source)));
		return this;
	}

	public Groups group(Param param){
		String source = param.source;
		String property = param.property;
		property=StringUtils.isEmpty(property)?source:property;
		this.groups.add(Group.grouping(property,buildAccumulator(param)));
		return this;
	}




	private static Accumulator buildAccumulator(Param param){
		String source = param.source;
		switch (param.future){
			case count:
				return new Accumulator("$sum",1);
			case first:
				return  first(source);
			case substr:
				return Accumulator.accumulator("$substr", Arrays.asList("$"+source,param.startIndex,param.endIndex));
			case avg:
				return average(source);
			case max:
				return max(source);
			case min:
				return min(source);
			case push:
				return push(source);
			case sum:
				return sum(source);
			case add2set:
				return addToSet(source);
			default:
				return first(source);
		}
	}


	public static class Param{

		private String source;

		private String property;

		private MFuture future;

		private int startIndex;

		private int endIndex;

		public Param(String source) {
			this.source = source;
		}

		public Param(String source, MFuture future) {
			this.source = source;
			this.future = future;
		}

		public Param(String source, String property) {
			this.source = source;
			this.property = property;
		}
		public Param(String source, String property,MFuture future) {
			this.source = source;
			this.property = property;
			this.future = future;
		}

		public Param setProperty(String property){
			this.property = property;
			return this;
		}

		public Param setStartIndex(int startIndex) {
			this.startIndex = startIndex;
			return this;
		}

		public Param setEndIndex(int endIndex) {
			this.endIndex = endIndex;
			return this;
		}
	}
}
