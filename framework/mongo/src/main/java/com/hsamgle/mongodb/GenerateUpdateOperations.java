package com.hsamgle.mongodb;

import com.hsamgle.mongodb.constant.Tag;
import com.hsamgle.mongodb.entity.Operators;
import com.hsamgle.mongodb.entity.UpdateObject;
import com.hsamgle.mongodb.exception.MongoException;
import com.hsamgle.mongodb.utils.FieldUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.query.UpdateOperations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  @feture   :	    TODO		根据更新信息拼凑出更新对象列表
 *	@file_name:	    GenerateUpdateOperations.java
 * 	@packge:	    com.hsamgle.mongodb
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:29
 *	@company:		江南皮革厂
 */
class GenerateUpdateOperations {

	/**
	 *
	 * @method:	TODO    构建更新对象
	 * @time  :	2018/5/21 9:28
	 * @author:	黄鹤老板
	 * @param ds
	* @param t
	* @param updateItems
	 * @return:     org.mongodb.morphia.query.UpdateOperations<T>
	 */
    static <T> UpdateOperations<T> getOperations( Datastore ds, Class<T> t, UpdateObject... updateItems){

        try {
            UpdateOperations<T> ops = ds.createUpdateOperations(t);
			boolean hasCreateTime = false;
            for (UpdateObject update : updateItems) {
                Object value = update.getValue();
	            String field = update.getField();

	            hasCreateTime = !hasCreateTime  && "updateTime".equals(field);

                // 字符串时遇到NULL值时，清空内容
                if("NULL".equals(value) && value.getClass().getName().contains("String")){
                    value = "";
                }
	            switch (update.getOperators()) {
                    case eq:
                        // 更新值
                        if(value!=null){
                            ops.set(field, value);
                        }
                        break;
                    case add:
                        // 往集合添加元素
                        if(value!=null){
                            ops.addToSet(field, value);
                        }
                        break;
                    case addAll:
                        // 往集合添加列表元素
                        ops.push(field, value);
                        break;
                    case unset:
                        // 删除某字段属性
                        ops.unset(field);
                        break;
                    case removeFirst:
                        // 删除集合中的第一项
                        ops.removeFirst(field);
                        break;
                    case removeLast :
                        // 删除集合的最后一项
                        ops.removeLast(field);
                        break;
                    case removeAll:
                        // 删除集合中符合条件的所有内容
                        ops.removeAll(field, value);
                        break;
                    case dec:
                        // 数值类型的字段递减
                        ops.dec(field);
                        break;
                    case inc:
                        // 数值类型字段递增
                        if(value!=null && value instanceof Number){
                            ops.inc(field,(Number)value);
                        }else{
                            ops.inc(field);
                        }
                        break;
                    default:
                        break;
                }
            }
	        Class<? super T> superclass = t.getSuperclass();
	        if(superclass!=null && superclass.getSimpleName().contains("MongoEntity") && !hasCreateTime){
	            ops.set("updateTime", System.currentTimeMillis());
            }

            return ops;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MongoException("数据格式类型有误");
        }
    }

	/**
	 *
	 * @method:	TODO    实现对实体转更新对象
	 * @time  :	2018/5/21 9:29
	 * @author:	黄鹤老板
	 * @param entity
	 * @return:     java.util.List<com.hsamgle.mongodb.entity.UpdateObject>
	 */
    static List<UpdateObject> entityToUpdateObject(Object entity){

        List<UpdateObject> listUpdate = new ArrayList<>();
	    List<Field> fields = FieldUtils.getAllFields(entity.getClass());
	    for (Field field : fields) {
            String key = field.getName();
            try {
                // 修改访问权限
                field.setAccessible(true);
                if (!"serialVersionUID".equals(key)) {
                    Object value = field.get(entity);
                    NotSaved notSaved = field.getAnnotation(NotSaved.class);
                    if (value != null && notSaved==null) {
                        if(Tag.INTEGER_CLEAR.equals(value) || Tag.LONG_CLEAR.equals(value) || Tag.STR_CLEAR.equals(value)){
                            listUpdate.add(new UpdateObject(key, Operators.unset));
                        }else{
                            listUpdate.add(new UpdateObject(key, value));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listUpdate;
    }

}
