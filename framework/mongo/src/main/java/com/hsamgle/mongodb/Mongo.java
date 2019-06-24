package com.hsamgle.mongodb;

import com.hsamgle.mongodb.entity.Condition;
import com.hsamgle.mongodb.entity.PageInfo;
import com.hsamgle.mongodb.entity.UpdateObject;
import com.hsamgle.mongodb.exception.MongoException;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.FindAndModifyOptions;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *
 *  @feture   :	    TODO		mongodb dao层实现类
 *	@file_name:	    Mongo.java
 * 	@packge:	    com.hsamgle.basic.mongodb
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:13
 *	@company:		江南皮革厂
 */
public final class Mongo {



    /**
     *
     * @method:	TODO    获取数据库连接对象
     * @time  :	2018/3/27 10:13
     * @author:	黄鹤老板
     * @param
     * @return:     org.mongodb.morphia.Datastore
     */
    private static  Datastore getDs(){
        return MongoDB.getDs();
    }

    /**
     *
     * @method:	TODO    执行单个或批量保存
     * @time  :	2018/3/27 10:14
     * @author:	黄鹤老板
     * @param entity
     * @return:     org.mongodb.morphia.Key<T>
     */
    public static <T> T save(T entity)throws Exception{

        if(entity == null){
            throw new MongoException("无法保存空文档");
        }
        Class<?> t = entity.getClass();
        Class<?> superclass = t.getSuperclass();
        if(superclass!=null && superclass.getSimpleName().contains("MongoEntity")){
            // 自动插入创建时间
            Field id = superclass.getDeclaredField("id");
            id.setAccessible(true);
            if(id.get(entity)==null){
                id.set(entity, UUID.randomUUID().toString().replace("-",""));
            }
            Field createTime = superclass.getDeclaredField("createTime");
            createTime.setAccessible(true);
            if(createTime.get(entity)==null){
                createTime.set(entity,System.currentTimeMillis());
            }
            Field updateTime = superclass.getDeclaredField("updateTime");
            updateTime.setAccessible(true);
            if(updateTime.get(entity)==null){
                updateTime.set(entity,System.currentTimeMillis());
            }
        }
        getDs().save(entity);
        return entity;
    }

    /**
     *
     * @method:	TODO    判断是否存在符合条件的数据
     * @time  :	2018/3/27 10:14
     * @author:	黄鹤老板
     * @param t
    * @param conditions
     * @return:     boolean
     */
    public static <T> boolean exists(Class<T> t,Condition... conditions) throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        query.retrievedFields(true, "_id");
        return query.get()!=null;
    }


    /**
     *
     * @method:	TODO    根据Id来查询某个字段
     * @time  :	2018/3/27 10:14
     * @author:	黄鹤老板
     * @param t
    * @param id
     * @return:     T
     */
    public static <T> T findOne(Class<T> t,String id)throws Exception{

        return getDs().get(t, id);
    }

    /**
     *
     * @method:	TODO    统计符合某添加的数据量
     * @time  :	2018/3/27 10:14
     * @author:	黄鹤老板
     * @param t
    * @param conditions
     * @return:     long
     */
    public static <T> long getMacthCount(Class<T> t,Condition... conditions) throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return ds.getCount(query);
    }


    /**
     *
     * @method:	TODO    根据条件来查询某一个对象
     * @time  :	2018/3/27 10:14
     * @author:	黄鹤老板
     * @param t
    * @param conditions
     * @return:     T
     */
    public static <T> T findOne(Class<T> t,Condition... conditions)throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return query.get();
    }

    /**
     *
     * @method:	TODO    查询某实体的列表对象
     * @time  :	2018/3/27 10:15
     * @author:	黄鹤老板
     * @param t
    * @param conditions
     * @return:     java.util.List<T>
     */
    public static <T> List<T> find(Class<T> t,Condition... conditions)throws Exception{
        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return query.asList();
    }

    /**
     *
     * @method:	TODO    分页排序查询
     * @time  :	2018/3/27 10:15
     * @author:	黄鹤老板
     * @param t
    * @param pNow
    * @param pSize
    * @param conditions
     * @return:     java.util.List<T>
     */
    public static <T> List<T> find(Class<T> t,int pNow,int pSize,Condition... conditions)throws Exception{

        // 拼接查询的条件
        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        pNow = pNow < 1?1:pNow;
        pSize = pSize < 1 ? 0:pSize;
        FindOptions options = new FindOptions();
        options.skip((pNow -1) * pSize);
        options.limit(pSize);
        return query.asList(options);
    }

    /**
     *
     * @method:	TODO    分页查询并返回封装好的分页对象
     * @time  :	2018/3/27 10:15
     * @author:	黄鹤老板
     * @param t
    * @param pNow
    * @param pSize
    * @param conditions
     * @return:     PageInfo<T>
     */
    public static <T> PageInfo<T> findInPage(Class<T> t, int pNow, int pSize, Condition... conditions) throws  Exception{
        long count = getMacthCount(t,conditions);
        List<T> list = null;
        if(count>0){
            list = find(t, pNow, pSize, conditions);
        }
        return new PageInfo<>(count, list, pNow, pSize);
    }



    /**
     *
     * @method:	TODO    根据条件来删除
     * @time  :	2018/3/27 10:16
     * @author:	黄鹤老板
     * @param t
    * @param conditions
     * @return:     com.mongodb.WriteResult
     */
    public static <T> WriteResult delete(Class<T> t,Condition... conditions)throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return  ds.delete(query);
    }

    /**
     *
     * @method:	TODO    根据id来删除文档
     * @time  :	2018/3/27 10:16
     * @author:	黄鹤老板
     * @param t
    * @param id
     * @return:     com.mongodb.WriteResult
     */
    public static <T, V> WriteResult deleteById(Class<T> t,V id)throws Exception{
        return getDs().delete(t, id);
    }

    /**
     *
     * @method:	TODO    根据主键来批量删除
     * @time  :	2018/3/27 10:17
     * @author:	黄鹤老板
     * @param t
    * @param ids
     * @return:     com.mongodb.WriteResult
     */
    public static <T, V> WriteResult deleteByIds(Class<T> t,Collection<V> ids) throws Exception{
        return getDs().delete(t, ids);
    }

    /**
     *
     * @method:	TODO    查找并删除
     * @time  :	2018/3/27 10:17
     * @author:	黄鹤老板
     * @param t
    * @param conditions
     * @return:     T
     */
    public static <T> T findAndDelete(Class<T> t,Condition... conditions) throws Exception{
        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return ds.findAndDelete(query);
    }

    /**
     *
     * @method:	TODO    多项更新
     * @time  :	2018/3/27 10:17
     * @author:	黄鹤老板
     * @param t
    * @param updateItems
    * @param autoCreate
    * @param conditions
     * @return:     org.mongodb.morphia.query.UpdateResults
     */
    public static <T> UpdateResults update(Class<T> t, List<UpdateObject> updateItems, boolean autoCreate, Condition... conditions)throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        UpdateOperations<T> ops = GenerateUpdateOperations.getOperations(ds,t, updateItems.toArray(new UpdateObject[]{}));
        return ds.update(query, ops,autoCreate);
    }

    /**
     *
     * @method:	TODO    单项更新
     * @time  :	2018/3/27 10:17
     * @author:	黄鹤老板
     * @param t
    * @param updateItems
    * @param autoCreate
    * @param conditions
     * @return:     org.mongodb.morphia.query.UpdateResults
     */
    public static <T> UpdateResults update(Class<T> t,UpdateObject updateItems,boolean autoCreate,Condition... conditions)throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        UpdateOperations<T> ops = GenerateUpdateOperations.getOperations(ds,t, updateItems);
        return ds.update(query, ops,autoCreate);
    }

    /**
     *
     * @method:	TODO    更新一条记录，出于性能的考虑，不推荐使用当前的更新方式
     * @time  :	2018/3/27 10:17
     * @author:	黄鹤老板
     * @param t
    * @param entity
    * @param autoCreate
    * @param conditions
     * @return:     org.mongodb.morphia.query.UpdateResults
     */
    @Deprecated
    public static <T> UpdateResults update(Class<T> t,T entity,boolean autoCreate,Condition... conditions)throws Exception{
        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return ds.updateFirst(query,entity,autoCreate);
    }

    /**
     *
     * @method:	TODO    通过实体的非空属性字段来更新内容
     * @time  :	2018/3/27 10:18
     * @author:	黄鹤老板
     * @param t
    * @param entity
    * @param autoCreate
    * @param conditions
     * @return:     org.mongodb.morphia.query.UpdateResults
     */
    public static <T> UpdateResults updateByField(Class<T> t,T entity,boolean autoCreate,Condition... conditions)throws Exception{

        Datastore ds = getDs();
        List<UpdateObject> updateItems = GenerateUpdateOperations.entityToUpdateObject(entity);
        UpdateOperations<T> ops = GenerateUpdateOperations.getOperations(ds,t, updateItems.toArray(new UpdateObject[]{}));
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        return ds.updateFirst(query,ops,autoCreate);
    }

    /**
     *
     * @method:	TODO    查找并更新符合条件的第一条数据
     * @time  :	2018/3/27 10:18
     * @author:	黄鹤老板
     * @param t
    * @param updateItems
    * @param autoCreate
    * @param conditions
     * @return:     T
     */
    public static <T> T findAndUpdate(Class<T> t,List<UpdateObject> updateItems,boolean autoCreate,Condition... conditions) throws Exception{

        Datastore ds = getDs();
        Query<T> query = GenerateQuery.getQuery(ds,t, conditions);
        UpdateOperations<T> ops = GenerateUpdateOperations.getOperations(ds,t, updateItems.toArray(new UpdateObject[]{}));
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
        // 默认返回就值
        findAndModifyOptions.returnNew(false);
        // 设置是否插入更新
        findAndModifyOptions.upsert(autoCreate);
        return ds.findAndModify(query, ops,findAndModifyOptions);
    }

    /**
     *
     * @method:	TODO    单字段更新
     * @time  :	2018/3/27 10:18
     * @author:	黄鹤老板
     * @param t
    * @param updateItem
    * @param autoCreate
    * @param conditions
     * @return:     T
     */
    public static <T> T findAndUpdate(Class<T> t,UpdateObject updateItem,boolean autoCreate,Condition... conditions) throws Exception{
        return findAndUpdate(t, Collections.singletonList(updateItem),autoCreate,conditions);
    }

}
