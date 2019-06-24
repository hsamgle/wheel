package com.hsamgle.mongodb.entity.aggregate;

import com.hsamgle.mongodb.entity.Condition;
import org.mongodb.morphia.query.Sort;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *  @feture   :	    TODO		执行数据聚合
 *	@file_name:	    AggregateBuilder.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:32
 *	@company:		江南皮革厂
 */
public final class AggregateBuilder<S,O> {

	private Class<S> source;

	private Class<O> out;

	private LinkedList<AggregateOptions> params;

	@SuppressWarnings("unchecked")
	public AggregateBuilder(Class<S> source, Class<O> out) {
		this.source = source;
		this.out = out;
		this.params = new LinkedList<>();
	}

	public Class<S> getSource() {
		return source;
	}

	public Class<O> getOut() {
		return out;
	}

	public LinkedList<AggregateOptions> getParams() {
		return params;
	}

	/**
	 *
	 * @method:	TODO    添加查询条件
	 * @time  :	2018/5/21 9:32
	 * @author:	黄鹤老板
	 * @param conditions
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder match(Condition... conditions){

		this.params.add(new Match(conditions));
		return this;
	}

	public AggregateBuilder match(List<Condition> conditions){
		if(conditions!=null){
			return match(conditions.toArray(new Condition[conditions.size()]));
		}
		return this;
	}

	/**
	 *
	 * @method:	TODO    将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值。
	 * @time  :	2018/5/21 9:32
	 * @author:	黄鹤老板
	 * @param unwind
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder unwind(String unwind){

		if(!StringUtils.isEmpty(unwind)){
			this.params.add(new Unwind(unwind));
		}
		return this;
	}

	/**
	 *
	 * @method:	TODO    设置排序规则
	 * @time  :	2018/5/21 9:32
	 * @author:	黄鹤老板
	 * @param sort
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder sort(String sort){

		if(!StringUtils.isEmpty(sort)){
			String[] split = sort.split(",");
			Sort[] sorts = new Sort[split.length];
			for ( int i = 0; i < split.length; i++ ) {
				String so = split[i];
				if(so.startsWith("-")){
					sorts[i] = Sort.descending(so.replace("-",""));
				}else{
					sorts[i] = Sort.ascending(so);
				}
			}
			this.params.add(new Sorts(sorts));
		}
		return this;
	}

	/**
	 *
	 * @method:	TODO    设置关联查询
	 * @time  :	2018/5/21 9:33
	 * @author:	黄鹤老板
	 * @param localField
	* @param from
	* @param foreignField
	* @param as
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder lookup(String localField, String from, String foreignField, String as){

		if(!StringUtils.isEmpty(localField)
			&& !StringUtils.isEmpty(from)
			&& !StringUtils.isEmpty(foreignField)
			&& !StringUtils.isEmpty(as)){
			this.params.add(new LookUp(localField,from,foreignField,as));
		}
		return this;
	}

	/**
	 *
	 * @method:	TODO    设置geo地里位置信息查询
	 * @time  :	2018/5/21 9:33
	 * @author:	黄鹤老板
	 * @param geo
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder geoNear(GeoCondition geo){
		if(geo!=null){
			AggregateOptions options = params.get(0);
			if(options!=null){
				// geonear 必须是要放在第一位，且是唯一的
				if(options instanceof GeoCondition){
					this.params.removeFirst();
				}
			}
			this.params.addFirst(geo);
		}
		return this;
	}

	/**
	 *
	 * @method:	TODO    设置分页
	 * @time  :	2018/5/21 9:33
	 * @author:	黄鹤老板
	 * @param pNow
	* @param pSize
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder inPage(Integer pNow, Integer pSize){

		this.params.add(new Page(pNow,pSize));
		return this;
	}


	/**
	 *
	 * @method:	TODO     修改输入文档的结构
	 * @time  :	2018/5/21 9:33
	 * @author:	黄鹤老板
	 * @param projects
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder project(Projects... projects){

		this.params.add(new Projects(projects));
		return this;
	}

	/**
	 *
	 * @method:	TODO    设置数据统计
	 * @time  :	2018/5/21 9:33
	 * @author:	黄鹤老板
	 * @param groups
	 * @return:     com.hsamgle.mongodb.entity.aggregate.AggregateBuilder
	 */
	public AggregateBuilder group(Groups groups){

		this.params.add(groups);
		return this;
	}

}
