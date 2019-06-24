package com.hsamgle.entity.params;

import com.hsamgle.basic.entity.SEntity;
import lombok.Data;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.entity.params
 * @ClassName
 * @Description
 * @date 2018/3/25 17:08
 * @company 广州讯动网络科技有限公司
 */
@Data
public class Param extends SEntity {

    private String id;

    private Integer integer;

    private Integer range;

    private String notNull;

    private String time;

    private Integer reg;

    /** 排除某些参数 */
    private String exclude;

    /** 必须要的参数 */
    private String need;
}
