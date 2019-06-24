package com.hsamgle.mongodb;

import com.hsamgle.mongodb.entity.Condition;
import com.hsamgle.mongodb.entity.PageInfo;
import com.hsamgle.mongodb.entity.aggregate.*;
import com.mongodb.BasicDBObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.GeoNear;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.aggregation.Projection;
import org.mongodb.morphia.query.Query;

import java.util.*;

/**
 *
 *  @feture   :	    TODO		实现对数据的聚合查询操作
 *	@file_name:	    Aggregation.java
 * 	@packge:	    com.hsamgle.mongodb
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:26
 *	@company:		江南皮革厂
 */
public class Aggregation {

	private static Log Log = LogFactory.getLog(Aggregation.class);

     /**
       *
       * @method:	TODO    聚合运算并直接返回结果列表
       * @time  :	2018/5/21 9:26
       * @author:	黄鹤老板
       * @param builder
       * @return:
       */
    @SuppressWarnings("unchecked")
    public static <O> List<O> aggregate(AggregateBuilder builder) throws  Exception{
        return (List<O>) aggregateInPage(builder).getData();
    }

	/**
	 *
	 * @method:	TODO    执行聚合运算并按分页结构返回
	 * @time  :	2018/5/21 9:26
	 * @author:	黄鹤老板
	 * @param builder
	 * @return:     com.hsamgle.mongodb.entity.PageInfo<O>
	 */
    @SuppressWarnings("unchecked")
    public static <S,O> PageInfo<O> aggregateInPage(AggregateBuilder builder) throws  Exception{

        Class<S> source = builder.getSource();
        Class<O> out = builder.getOut();

        Datastore ds = MongoDB.getDs();
        AggregationPipeline pipeline = ds.createAggregation(source);

        // 这里保存了分页信息
        Page page = new Page(1,100);

        // 这里保存的查询的参数
	    LinkedList<AggregateOptions> params = builder.getParams();
	    for (AggregateOptions options : params) {
	    	if(options==null){
				continue;
		    }
			switch (options.getType()){
			case GEO:
				GeoNear geoNear = buildGeoNear(ds, source, (GeoCondition) options);
				pipeline.geoNear(geoNear);
				break;
			case PAGE:
				page = (Page) options;
				break;
			case SORT:
				Sorts sorts = (Sorts) options;
				pipeline.sort(sorts.getSorts());
				break;
			case GROUP:
				Groups groups = (Groups) options;
				List<Group> ids = groups.getIds();
				List<Group> gs = groups.getGroups();
				pipeline.group(ids,gs.toArray(new Group[gs.size()]));
				break;
			case MATCH:
				Match match = (Match) options;
				Query<S> query = GenerateQuery.getQuery(ds, source,false, match.getConditions());
				pipeline.match(query);
				break;
			case UNWIND:
				Unwind unwind = (Unwind) options;
				pipeline.unwind(unwind.getUnwind());
				break;
			case LOOK_UP:
				LookUp lookUp = (LookUp) options;
				pipeline.lookup(lookUp.getFrom(),lookUp.getLocalField(),lookUp.getForeignField(),lookUp.getAs());
				break;
			case PROJECT:
				Projects projects = (Projects) options;
				List<Projection> projections = buildProjections(projects);
				pipeline.project(projections.toArray(new Projection[projections.size()]));
				break;
			default:
				break;
			}
	    }

	    System.out.println(pipeline.toString());

	    Log.debug(pipeline.toString());

        Iterator<String> aggregate = pipeline.aggregate(String.class);
        int count = 0;
        while ( aggregate.hasNext() ){
            aggregate.next();
            count++;
        }

		if(page.getSkip()>1){
            pipeline.skip(page.getSkip());
		}

        if(page.getLimit()>1){
            pipeline.limit(page.getLimit());
        }

        List<O> data = new ArrayList<>();
        Iterator<O> iterator = pipeline.aggregate(out);
        while (iterator.hasNext() ){
	        O next = iterator.next();
	        data.add(next);
        }

        return   new PageInfo<>(count,data,page.getpNow(),page.getpNow());
    }

	/**
	 *
	 * @method:	TODO    构建 Geo 查询对象
	 * @time  :	2018/5/21 9:27
	 * @author:	黄鹤老板
	 * @param ds
	* @param source
	* @param geo
	 * @return:     org.mongodb.morphia.aggregation.GeoNear
	 */
    private static GeoNear buildGeoNear(Datastore ds,Class source,GeoCondition geo) throws Exception {
        Condition[] conditions = geo.getConditions();
        Query<?> query = GenerateQuery.getQuery(ds,source.getClass(),conditions);
        return  new GeoNear
                .GeoNearBuilder(geo.getDistanceField())
                .setNear(geo.getLatitude(),geo.getLongitude())
                .setLimit(geo.getLimit())
                .setMaxDistance(geo.getRadius())
                .setSpherical(geo.getSpherical())
                .setQuery(query).build();
    }

	/**
	 *
	 * @method:	TODO    构建投影项
	 * @time  :	2018/5/21 9:27
	 * @author:	黄鹤老板
	 * @param projects
	 * @return:     java.util.List<org.mongodb.morphia.aggregation.Projection>
	 */
    private static List<Projection> buildProjections(Projects projects){

	    Projects[] options = projects.getProjects();
	    List<Projection> projections = new ArrayList<>();
	    for (Projects option : options) {
	        String property = option.getProperty();
	        String source = option.getSource();
	        Projects[] ps = option.getProjects();
	        if(ps!=null) {
		        for (Projects p : ps) {
			        projections.add(Projection.projection(p.getSource()));
		        }
	        }else if(option.getExpression()!=null){
			        switch (option.getExpression()){
				        case dateToString:
					        projections.add(Projection.projection(property,
							        Projection.expression("$dateToString", new BasicDBObject("format", "%Y-%m-%d")
									        .append("date", new BasicDBObject("$add", Arrays.asList(new Date(0),"$"+source))))));
					        break;
				        default:
					        break;
			        }
		        }else if(property !=null){
			        projections.add(Projection.projection(source, property));
		        }else{
			        Projection projection = Projection.projection(source);
			        projections.add(option.getRequest()?projection:projection.suppress());
		        }
	        }
        return  projections;
    }


}
