package com.hsamgle.service.inter;

import com.hsamgle.entity.mysql.UserInfo;
import com.hsamgle.basic.entity.ResponseEntity;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.service.inter
 * @ClassName
 * @Description
 * @date 2018/3/25 17:24
 * @company 广州讯动网络科技有限公司
 */
public interface IUserService {


    ResponseEntity add( UserInfo user);

    ResponseEntity getById(Integer id);

    ResponseEntity list( UserInfo user);

    ResponseEntity update(UserInfo user);

    ResponseEntity delete(Integer id);




    ResponseEntity queryByMybatis(Integer id);

}
