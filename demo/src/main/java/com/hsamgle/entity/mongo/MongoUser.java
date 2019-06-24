package com.hsamgle.entity.mongo;

import com.hsamgle.basic.entity.MongoEntity;
import lombok.Data;
import org.mongodb.morphia.annotations.Entity;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.entity.mongo
 * @ClassName
 * @Description
 * @date 2018/3/25 16:28
 * @company 广州讯动网络科技有限公司
 */
@Data
@Entity(value = "mongo_user",noClassnameStored = true)   // 这里的value 声明了 这个实体对应的文档，noClassnameStored = true 设置不保存这个类的类名
public class MongoUser extends MongoEntity {

    private String userName;

    private Integer gender;

    private Integer age;



}
