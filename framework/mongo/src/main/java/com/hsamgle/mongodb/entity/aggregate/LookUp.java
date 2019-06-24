package com.hsamgle.mongodb.entity.aggregate;


/**
 *
 *  @feture   :	    TODO		使用mongodb的  $lookup 功能实现多表关联查询
 *	@file_name:	    LookUp.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:37
 *	@company:		江南皮革厂
 */
public final class LookUp extends AggregateOptions {

    /** 本表关联字段 */
    private String localField;

    /** 关联表的 */
    private String from;

    /** 关联表主键  */
    private String foreignField;

    /** 映射结果集 */
    private String as;

    private LookUp ( ) {}

    public LookUp ( String localField, String from, String foreignField, String as ) {
        this.localField = localField;
        this.from = from;
        this.foreignField = foreignField;
        this.as = as;
        super.setType(Type.LOOK_UP);
    }

    public String getLocalField ( ) {
        return localField;
    }

    public String getFrom ( ) {
        return from;
    }

    public String getForeignField ( ) {
        return foreignField;
    }

    public String getAs ( ) {
        return as;
    }

}
