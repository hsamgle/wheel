package com.hsamgle.basic.entity;

import com.hsamgle.basic.annotation.ParamsValid;
import com.hsamgle.basic.utils.DateTimeUtils;
import com.hsamgle.basic.utils.PatternUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;


/**
 *
 *  @feture   :	    TODO		实体类父类
 *	@file_name:	    DataEntity.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:12
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataEntity extends SEntity{

	private static final long serialVersionUID = -3699663004699390819L;

    @org.mongodb.morphia.annotations.Transient
    @javax.persistence.Transient
    public transient Integer pNow;

    @org.mongodb.morphia.annotations.Transient
    @javax.persistence.Transient
    public transient Integer pSize;

    /** 排序字段  降序的话 用 -fieldName 表示 升序 用 fieldName 表示  */
    @org.mongodb.morphia.annotations.Transient
    @javax.persistence.Transient
    public transient String sort;

    /** 开始日期 */
    @ParamsValid(isTime = true)
    @org.mongodb.morphia.annotations.Transient
    @javax.persistence.Transient
    public transient String st;

    /** 结束日期 */
    @ParamsValid(isTime = true)
    @org.mongodb.morphia.annotations.Transient
    @javax.persistence.Transient
    public transient String et;

    public Integer getpNow ( ) {
        return getpNow(1);
    }

    public Integer getpSize ( ) {
        return getpSize(20);
    }

    public Integer getpNow (int def ) {
        return pNow == null ? (def < 1 ? 1:def):pNow;
    }

    public Integer getpSize ( int def) {
        return pSize == null ? (def < 1 ? 1:def):pSize;
    }

    public Long getSt (TimeUnit unit) {
	    try {
	        if(st==null){
	            return null;
            }
            long time = PatternUtils.isDateTime(st)?DateTimeUtils.string2Long(st,"yyyy-MM-dd HH:mm:ss"):DateTimeUtils.string2Long(st);
            return TimeUnit.SECONDS.equals(unit)?time/1000:time;
	    }catch (Exception e){
	        return null;
	    }
    }

    public Long getEt ( TimeUnit unit) {
        try {
            if(et==null){
                return null;
            }
            long time  = PatternUtils.isDateTime(et)?DateTimeUtils.string2Long(et,"yyyy-MM-dd HH:mm:ss"):DateTimeUtils.string2Long(et) + DateTimeUtils.ONE_DAY;
            return TimeUnit.SECONDS.equals(unit)?time/1000:time;
        }catch (Exception e){
            return null;
        }
    }

    public String getSort ( String def) {
        return StringUtils.isEmpty(sort)?def:sort;
    }

}
