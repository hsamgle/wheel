package com.hsamgle.controller;

import com.hsamgle.basic.annotation.ConsumeTime;
import com.hsamgle.basic.annotation.ParamsValid;
import com.hsamgle.basic.controller.BaseController;
import com.hsamgle.entity.mysql.UserInfo;
import com.hsamgle.service.inter.IUserService;
import com.hsamgle.basic.entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.controller
 * @ClassName
 * @Description
 * @date 2018/3/25 16:09
 * @company 广州讯动网络科技有限公司
 */
@RestController
public class MySqlController extends BaseController {


    /**
     *
     *
     *              在这个controller中，主要演示了MySQL的相关的 CURD
     *
     *
     */

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "users",method = RequestMethod.POST)
    public ResponseEntity addUser( @ParamsValid(isEntity = true) UserInfo userInfo){
        return userService.add(userInfo);
    }

    @RequestMapping(value = "users",method = RequestMethod.GET)
    public ResponseEntity getUsers(@ParamsValid(isEntity = true) UserInfo userInfo){
        return userService.list(userInfo);
    }



}
