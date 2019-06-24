package com.hsamgle.mongodb;

import com.hsamgle.mongodb.entity.Condition;
import com.hsamgle.mongodb.entity.Operators;
import com.hsamgle.mongodb.entity.aggregate.GeoCondition;
import com.hsamgle.mongodb.utils.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.AbstractCriteria;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.WhereCriteria;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 *  @feture   :	    TODO		构建查询条件
 *	@file_name:	    GenerateQuery.java
 * 	@packge:	    com.hsamgle.mongodb
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:28
 *	@company:		江南皮革厂
 */
class GenerateQuery {

	private static Log LOG = LogFactory.getLog(GenerateQuery.class);

	/**
	 *
	 * @method:	TODO    生成查询条件
	 * @time  :	2018/5/21 9:28
	 * @author:	黄鹤老板
	 * @param ds
	* @param t
	* @param conditions
	 * @return:     org.mongodb.morphia.query.Query<T>
	 */
    protected static <T> Query<T> getQuery(Datastore ds, Class<T> t, Condition... conditions)throws Exception{

        return getQuery(ds,t,true,conditions);
    }




	/**
	 *
	 * @method:	TODO    生成查询条件
	 * @time  :	2018/5/21 9:27
	 * @author:	黄鹤老板
	 * @param ds
	* @param t
	* @param disableValidation
	* @param conditions
	 * @return:     org.mongodb.morphia.query.Query<T>
	 */
    protected static <T> Query<T> getQuery(Datastore ds, Class<T> t,boolean disableValidation, Condition... conditions)throws Exception{

        Query<T> query = ds.createQuery(t);
        if(!disableValidation){
            query.disableValidation();
        }

	    List<Condition> ors = new ArrayList<>();
	    List<Condition> ands = new ArrayList<>();

        for (Condition condition : conditions) {
        	if(condition!=null){
		        Operators operator = condition.getOperator();
		        String key = condition.getKey();
		        Object value = condition.getValue();
		        switch (operator){
			        case request:
				        if (!StringUtils.isEmpty(key)) {
					        String[] split = key.split(",");
					        for (String k : split) {
						        query.project(k, true);
					        }
				        }
				        break;
			        case ingnore:
				        if (!StringUtils.isEmpty(key)) {
					        String[] split = key.split(",");
					        for (String k : split) {
						        query.project(k, false);
					        }
				        }
				        break;
			        case sort:
				        if (!StringUtils.isEmpty(key)) {
					       query.order(key);
				        }
				        break;
			        case l_like:
				        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
					        Pattern pattern = Pattern.compile(value + "$", Pattern.CASE_INSENSITIVE);
					        query.filter(key, pattern);
				        }
				        break;
			        case r_like:
				        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
					        Pattern pattern = Pattern.compile("^" + value, Pattern.CASE_INSENSITIVE);
					        query.filter(key, pattern);
				        }
				        break;
			        default:
			        	if(key!=null && value!=null){
					        if(condition.isOr()){
						        ors.add(condition);
					        }else{
					            ands.add(condition);
					        }
				            break;
				        }
		        }

	        }
        }

	    query.and(getQuery(query,ands).toArray(new AbstractCriteria[]{}));
        if(!ors.isEmpty()){
	        query.or(getQuery(query,ors).toArray(new AbstractCriteria[]{}));
        }
		LOG.debug(query.toString());
        return query;
    }



	/**
	 *
	 * @method:	TODO    构建查询对象
	 * @time  :	2018/5/21 9:27
	 * @author:	黄鹤老板
	 * @param query
	* @param conditions
	 * @return:     java.util.List<org.mongodb.morphia.query.AbstractCriteria>
	 */
    private static <T> List<AbstractCriteria>  getQuery(Query<T> query, List<Condition> conditions) throws IllegalAccessException {

	    List<AbstractCriteria> queries = new ArrayList<>(conditions.size());
	    for (Condition condition : conditions) {
		    if (condition != null) {
			    String key = condition.getKey();
			    Object value = condition.getValue();
			    Operators operator = condition.getOperator();
			    switch (operator != null ? operator : Operators.def) {

				    case obj:
					    if (value == null) {
						    continue;
					    }
					    List<Field> fields = FieldUtils.getAllFields(value.getClass());
					    for (Field field : fields) {
						    String fieldName = field.getName();
						    field.setAccessible(true);
						    Object fieldVal = field.get(value);
						    if (fieldVal != null) {
							    queries.add(query.criteria(fieldName).equal(fieldVal));
						    }
					    }
					    break;
				    case eq:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).equal(value));
					    }
					    break;
				    case ne:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).notEqual(value));
					    }
					    break;
				    case gt:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).greaterThan(value));
					    }
					    break;
				    case lt:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).lessThan(value));
					    }
					    break;
				    case gte:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).greaterThanOrEq(value));
					    }
					    break;
				    case lte:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).lessThanOrEq(value));
					    }
					    break;
				    case in:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).in((Iterable<?>) value));
					    }
					    break;
				    case nin:
					    if (!StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).notIn((Iterable<?>) value));
					    }
					    break;
				    case exists:
					    if (!StringUtils.isEmpty(key)) {
						    queries.add(query.criteria(key).exists());
					    }
					    break;
				    case nexists:
					    if (!StringUtils.isEmpty(key)) {
						    queries.add(query.criteria(key).doesNotExist());
					    }
					    break;
				    case like:
					    if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
						    queries.add(query.criteria(key).containsIgnoreCase(value + ""));
					    }
					    break;
				    case elem:
					    if (value != null) {
						    queries.add(query.criteria(key).hasThisElement(value));
					    }
					    break;
				    case where:
					    if (value != null) {
						    queries.add(new WhereCriteria(value + ""));
					    }
					    break;
				    case near:
					    if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value) && value instanceof GeoCondition) {
						    GeoCondition geo = (GeoCondition) value;
						    Boolean spherical = geo.getSpherical();
						    Double radius = geo.getRadius();
						    Double longitude = geo.getLongitude();
						    Double latitude = geo.getLatitude();
						    int count = geo.getCount();
						    switch (count) {
							    case 1:
								    queries.add(query.criteria(key).near(longitude, latitude, radius));
								    break;
							    case 2:
								    queries.add(query.criteria(key).near(longitude, latitude, spherical));
								    break;
							    case 3:
								    queries.add(query.criteria(key).near(longitude, latitude, radius, spherical));
								    break;
							    default:
								    queries.add(query.criteria(key).near(longitude, latitude));
								    break;
						    }
					    }
				    default:
					    break;
			    }
		    }
	    }
	    return queries;
    }

}
