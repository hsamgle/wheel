package ex.com.hsamgle.mysql.base;

import com.hsamgle.mysql.entity.SqlCondition;
import com.hsamgle.mysql.entity.SqlOperators;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @类功能:	TODO	封装复杂条件查询
 *	@文件名:	BaseExample.java
 * 	@所在包:	ex.com.com.dplus.mysql.base
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2017年4月20日上午11:53:31
 *	@公_司:		广州讯动网络科技有限公司
 */
public class BaseExample extends Example{


	private BaseExample ( Class<?> entityClass ) {
		super(entityClass);
	}

    /**
     *
     * @方法功能：	TODO    获取Example对象
     * @编写时间：	2018/3/22 21:45
     * @author：	黄先国 | hsamgle@qq.com
     * * @param t
     * @return     tk.mybatis.mapper.entity.Example
     */
	public static <T> BaseExample getExample(Class<T> t){

		return new BaseExample(t);
	}

	/**
	 * 
	 * @方法功能：	TODO		根据实体自身的参数来构造查询条件，选择非空参数作为条件
	 * @方法名称：	buildYourself
	 * @编写时间：	2017年5月5日上午11:03:49
	 * @开发者  ：	  黄先国
	 * @方法参数：	@param t
	 * @方法参数：	@param condition
	 * @方法参数：	@return
	 * @返回值  :	Example
	 */
	public  BaseExample buildYourself(Object condition) {
		return build(new SqlCondition(condition));
	}
	
	/**
	 * 
	 * @方法功能：	TODO	使用list集合条件参数作为入参
	 * @方法名称：	build
	 * @编写时间：	2017年5月5日上午11:04:47
	 * @开发者  ：	  黄先国
	 * @方法参数：	@param t
	 * @方法参数：	@param conditions
	 * @方法参数：	@return
	 * @返回值  :	Example
	 */
	public BaseExample build(Collection<SqlCondition> conditions) {
		SqlCondition[] sqlConditions = conditions.toArray(new SqlCondition[conditions.size()]);
		return build(sqlConditions);
	}
	
	
	/**
	 * 
	 * @方法功能：	TODO	使用的数据集合条件参数作为入参
	 * @方法名称：	build
	 * @编写时间：	2017年5月5日上午11:05:09
	 * @开发者  ：	  黄先国
	 * @方法参数：	@param t
	 * @方法参数：	@param conditions
	 * @方法参数：	@return
	 * @返回值  :	Example
	 */
	public BaseExample build(SqlCondition... conditions) {
		Criteria criteria = this.createCriteria();
		for (SqlCondition condition : conditions) {
			
			SqlOperators operator = condition.getOperator();
			String key = condition.getKey();
			Object value = condition.getValue();

			switch (operator) {
			case entity:
				if(value!=null){
					criteria.andEqualTo(value);
				}
				break;
			case request:
				if(!StringUtils.isEmpty(key)){
					this.selectProperties(key.split(","));
				}
				break;
			case eq:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andEqualTo(key, value);
				}
				break;
			case ne:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andNotEqualTo(key, value);
				}
				break;
			case gt:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andGreaterThan(key, value);
				}
				break;
			case gte:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andGreaterThanOrEqualTo(key, value);
				}
				break;
			case lt:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andLessThan(key, value);
				}
				break;
			case lte:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andLessThanOrEqualTo(key, value);
				}
				break;
			case in:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value) && (value instanceof Iterable<?>)){
					criteria.andIn(key, (Iterable<?>)value);
				}
				break;
			case nin:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value) && (value instanceof Iterable<?>)){
					criteria.andNotIn(key, (Iterable<?>)value);
				}
				break;
			case exists:
				if(!StringUtils.isEmpty(key)){
					criteria.andIsNotNull(key);
				}
				break;
			case nexists:
				if(!StringUtils.isEmpty(key)){
					criteria.andIsNull(key);
				}
				break;
			case like:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andLike(key,"%"+value.toString()+"%");
				}
				break;
			case llike:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andLike(key,"%"+value.toString());
				}
				break;
			case rlike:
				if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
					criteria.andLike(key,value.toString()+"%");
				}
				break;
			case sort:
				if(!StringUtils.isEmpty(key)){
					if(key.startsWith("-")){
						key = key.replace("-", "");
						this.orderBy(key).desc();
					}else{
						this.orderBy(key).asc();
					}
				}
				break;
			default:
				break;
			}
			
		}
		return this;
	}
	
	
}
