package com.hsamgle.mysql.aspect;

import com.hsamgle.mysql.annotation.PK;
import com.hsamgle.mysql.constant.PKType;
import com.hsamgle.mysql.entity.SqlCondition;
import com.hsamgle.mysql.utils.ClassUtils;
import com.hsamgle.mysql.utils.FieldUtils;
import com.hsamgle.mysql.utils.SnowflakeId;
import ex.com.hsamgle.mysql.base.BaseExample;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.hsamgle.mysql.constant.PKType.ORDER;

/**
 * @类功能: TODO        定义mysql持久层的切面
 * @文件名: MysqlAspect.java
 * @所在包: cn.com.dplus.mysql.aop
 * @开发者: 黄先国
 * @邮_件: hsamgle@qq.com
 * @时_间: 2017年3月7日下午4:30:43
 * @公_司: 广州讯动网络科技有限公司
 */

@Aspect
@Component
public class MysqlAspect implements Ordered {

    /** 定义的只读的正则表达是  */
    private static  final Pattern READ = Pattern.compile("(get)|(query)|(select)|(find)|(exist)|(whether)|(count)|(check)|(look)");
    private static  final Pattern WRITE = Pattern.compile("(insert)|(insertSelective)|(insertUseGeneratedKeys)|(updateByPrimaryKey)|(updateByPrimaryKeySelective)|(updateByExample)|(updateByExampleSelective)");


	@Value("${mysql.aliases_package}")
	private String entityPackge;


	/** 定义切点Pointcut */
	@Pointcut("within(ex.com.hsamgle.mysql.base.BaseMapper+)")
	public void excuteDao() {}


    @Around("excuteDao()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获取里面的参数
        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();


        // 当前操作的泛型t
        Class<?> t = null;

        // 根据DAO操作接口上的注解来选择数据源
        Type[] genericInterfaces = target.getClass().getGenericInterfaces();
        if (genericInterfaces.length > 0) {
	        Type type = genericInterfaces[0];
	        Class<?> mapperInterface = Class.forName(type.getTypeName());
	        Type[] interfaces = mapperInterface.getGenericInterfaces();
	        if(interfaces.length>0){
		        Type anInterface = interfaces[0];
		        if(anInterface instanceof ParameterizedType){
			        Type[] types = ((ParameterizedType) anInterface).getActualTypeArguments();
			        t =  Class.forName(types[0].getTypeName());
		        }
	        }
        }

        MethodSignature signature = (MethodSignature)  pjp.getSignature();
        Method currentMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        String method = currentMethod.getName();

        // 是否是调用了Example方法
        if(method.contains("Example")){
			handleExample(t,args);
        }

        // 是否是写类操作
        if(WRITE.matcher(method).find()){
			handleWrite(method,args);
        }else if(READ.matcher(method).find()){
            // 处理读方法的参数适配
			handleRead(args);
        }

        return pjp.proceed(args);
    }


    @Override
    public int getOrder() {
        return 0;
    }




	/**
	 *
	 * @方法功能：	TODO    完成 tk.mybatis.Example 的转化型封装
	 * @编写时间：	2018/8/1 16:11
	 * @author：	黄先国 | hsamgle@qq.com
	 * * @param objects
	 * @return     void
	 */
	@SuppressWarnings("unchecked")
    private static <T> void handleExample(Class<T> t,Object... args){
    	if(args.length>0){
		    Object arg = args[args.length - 1];
		    if(arg==null || t ==null){
		    	return;
		    }
			BaseExample example = BaseExample.getExample(t);
			if(arg instanceof SqlCondition){
				args[args.length - 1] = example.build((SqlCondition) arg);
			}else if(arg instanceof SqlCondition[]){
				args[args.length - 1] = example.build((SqlCondition[]) arg);
			}else if(arg instanceof Collection){
				args[args.length - 1] = example.build((Collection<SqlCondition>) arg);
			}else if(t.isInstance(arg)){
				args[args.length - 1] = example.buildYourself(arg);
			}
	    }
    }

	private static void handleWrite(String method,Object... objects) throws IllegalAccessException {

		if(objects!=null){
			for (int i = 0; i < objects.length; i++) {
				Object object = objects[i];
				if(object==null){
					continue;
				}

				if(object instanceof Collection){
					Collection<?> objs = (Collection<?>) object;
					for ( Object obj : objs ) {
						handle(method,obj);
					}
				}else if(object.getClass().isArray()){
					Object[] objs = (Object[]) object;
					for ( Object obj : objs ) {
						handle(method,obj);
					}
				}else if ("NULL".equals(object) && object instanceof String){
					objects[i] = "";
				}else{
				    handle(method,object);
                }
			}
		}
	}


	private static void handle(String method ,Object object) throws IllegalAccessException {
		Class<?> clz = object.getClass();
		Class<?> mysqlEntityClass = ClassUtils.getMysqlEntityClass();
		if(mysqlEntityClass!=null && mysqlEntityClass.isInstance(object)) {
			List<Field> allFields = FieldUtils.getAllFields(clz);
			// 是实体类型
			for (Field field : allFields) {
				String fieldName = field.getName();
				field.setAccessible(true);
				if (method.startsWith("insert")) {

					if("id".equals(fieldName)){
						// 根据主键类型来做出反应
						PK pk = clz.getAnnotation(PK.class);
						Object id = getId(pk);
						if(ObjectUtils.isEmpty(field.get(object)) && id != null){
							field.set(object,id);
						}
					}else if ("createTime".equals(fieldName) || "updateTime".equals(fieldName)) {
						if (field.get(object) == null) {
							field.set(object, getTimestamp());
						}
					}else if("state".equals(fieldName)){
						if(field.get(object)==null){
							// 默认state字段为1
							field.set(object,1);
						}
					}
				} else if (method.startsWith("update") && "updateTime".equals(fieldName)) {
					if (field.get(object) == null) {
						field.set(object, getTimestamp());
					}
				} else if ("NULL".equals(field.get(object)) && field.getType() == String.class) {
					field.set(object, "");
				}
			}
		}
	}


	private static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}


	/**
	 *
	 * @method:	TODO    主键生成策略
	 * @time  :	2018/10/15 21:47
	 * @author:	黄鹤老板
	 * @param pk
	 * @return:     java.lang.Object
	 */
	private static Object getId(PK pk){

		PKType type = pk!=null?pk.generator():ORDER;

		switch (type){
			case ORDER:
				return SnowflakeId.getNextId();
			case UUID:
				return UUID.randomUUID().toString().replace("-","");
			case Second:
				return System.currentTimeMillis() / 1000;
			case MilliSecond:
				return System.currentTimeMillis();
			case NanoSecond:
				return System.nanoTime();
			case AUTO_INC:
				return null;
			default:
				return SnowflakeId.getNextId();
		}
	}


	/**
	 *
	 * @方法功能：	TODO    处理查询参数的适配性转换
	 * @编写时间：	2018/8/24 15:23
	 * @author：	黄先国 | hsamgle@qq.com
	 * * @param args
	 * @return     void
	 */
	private static void handleRead(Object... args) throws IllegalAccessException {
		if(args!=null){
			for (Object object : args) {
				if(object==null){
					continue;
				}
				Class<?> clz = object.getClass();
				Class<?> baseEntityClass = ClassUtils.getBaseEntityClass();
				if(baseEntityClass!=null && baseEntityClass.isInstance(object)) {
					List<Field> allFields = FieldUtils.getAllFields(clz);
					for (Field field : allFields) {
						String fieldName = field.getName();
						if ("sort".equals(fieldName)) {
							field.setAccessible(true);
							Object sort = field.get(object);
							if(sort!=null){
								String s = (String) sort;
								String[] sorts = s.split(",");
								StringBuilder builder = new StringBuilder();
								for (String ss : sorts) {
									if(ss.startsWith("-")){
										builder.append(FieldUtils.switchCase(ss.replace("-",""))).append(" DESC").append(",");
									}else{
										builder.append(FieldUtils.switchCase(ss)).append(" ASC").append(",");
									}
								}
								builder.deleteCharAt(builder.length()-1);
								field.set(object,builder.toString());
							}
							// 暂时只做到处理sort字段吧
							break;
						}
					}
				}
			}
		}
	}




}
