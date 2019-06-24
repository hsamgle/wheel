package com.hsamgle.mongodb.entity.aggregate;


import com.hsamgle.mongodb.entity.Condition;

import java.util.List;

/**
 *
 *  @feture   :	    TODO		这里封装的是geo地理查询条件
 *	@file_name:	    GeoCondition.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:34
 *	@company:		江南皮革厂
 */
public final class GeoCondition extends AggregateOptions {

    /** 查询的字段 */
    private String distanceField;

    /** 经度 */
    private Double longitude;

    /** 维度 */
    private Double latitude;

    /** 距离中心的半径距离  DDD 格式*/
    private Double radius;

    /** 是否是3D查询  */
    private Boolean spherical;

    /** 查询条件 */
    private Condition[] conditions;

    /** 返回最大的匹配文档数  */
    private Long limit;

    private transient int count;

    public GeoCondition(){
        super.setType(Type.GEO);
    }


    public GeoCondition(Double longitude,Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
        super.setType(Type.GEO);
    }
    public String getDistanceField ( ) {
        return distanceField;
    }

    public void setDistanceField ( String distanceField ) {
        this.distanceField = distanceField;
    }

    public Double getLongitude ( ) {
        return longitude;
    }

    public void setLongitude ( Double longitude ) {
        this.longitude = longitude;
    }

    public Double getLatitude ( ) {
        return latitude;
    }

    public void setLatitude ( Double latitude ) {
        this.latitude = latitude;
    }

    public Double getRadius ( ) {
        return radius;
    }

    public void setRadius ( Double radius ) {
        this.radius = radius==null?0.00005:radius;
        this.count += 1;
    }

    public Boolean getSpherical ( ) {
        return spherical;
    }

    public void setSpherical ( Boolean spherical ) {
        this.spherical = spherical;
        this.count += 2;
    }

    public int getCount ( ) {
        return count;
    }

    public Condition[] getConditions ( ) {
        return conditions==null?new Condition[0]:conditions;
    }

    public void setConditions ( List<Condition> conditions ) {
        this.conditions = conditions == null ? new Condition[0]:conditions.toArray(new Condition[conditions.size()]);
    }
    public void setConditions ( Condition[] conditions ) {
        this.conditions = conditions;
    }

    public Long getLimit ( ) {
        // 默认最多返回100条数据
        return limit!=null?limit:100;
    }

    public void setLimit ( Long limit ) {
        this.limit = limit;
    }
}
