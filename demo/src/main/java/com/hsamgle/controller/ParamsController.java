package com.hsamgle.controller;

import com.hsamgle.basic.annotation.ConsumeTime;
import com.hsamgle.basic.annotation.ParamsValid;
import com.hsamgle.basic.controller.BaseController;
import com.hsamgle.entity.params.Param;
import com.hsamgle.basic.entity.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.controller
 * @ClassName
 * @Description
 * @date 2018/3/25 16:08
 * @company 广州讯动网络科技有限公司
 */
@RestController
public class ParamsController extends BaseController {


    /***
     *
     *
     *
     *          在这个controller中主要演示了  请求时的参数校验等
     *
     *
     *
     *
     *
     */


    @RequestMapping(value = "get",method = RequestMethod.GET)
    public ResponseEntity get(){
        return ResponseEntity.build("这是get请求");
    }

    @RequestMapping(value = "post",method = RequestMethod.POST)
    public ResponseEntity post(){
        return ResponseEntity.build("这是post请求");
    }

    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public ResponseEntity delete(){
        return ResponseEntity.build("这是delete请求");
    }

    @RequestMapping(value = "put",method = RequestMethod.PUT)
    public ResponseEntity put(){
        return ResponseEntity.build("这是put请求");
    }


    /**
     *
     * @方法功能：	TODO    这里演示了参数校验
     * @编写时间：	2018/3/25 17:17
     * @author：	黄先国 | hsamgle@qq.com
     * * @param id      path 参数
    * @param integer   要求是整型参数
    * @param range     要求参数在范围内
    * @param time      要求参数是时间格式YYYY-MM-dd
    * @param notNull   要求参数非空
    * @param reg       要求参数符合指定的正则表达式
     * @return     ResponseEntity
     */
    @RequestMapping(value = "params/{id}",method = RequestMethod.GET)
    public ResponseEntity params(

            @PathVariable("id") String id,                   // 指定从路径上获取参数  假如 路径上的参数和形参相同，则 @PathVariable("id")  可以省略为 @PathVariable
            @ParamsValid(isInt = true) Integer integer,     // 指定当前的参数类型指定为 正数类型，如果不是正数类型的话，则会拦截当前请求并返回相应信息
            @ParamsValid(max = 10,min = 1) Integer range,   //  指定当前参数的值的有效范围  如果超过范围则会拦截当前请求并返回相应信息
            @ParamsValid(isTime = true) String time,        // 指定当前参数格式为时间类型  yyyy-MM-dd ，不符合相关格式的则会拦截当前请求并返回相应信息
            @ParamsValid(notNull = true) String notNull,    // 指定当前参数不能为空
            @ParamsValid(reg = "^[0-1]$") Integer reg ){    // 指定当前参数的正则表达式，如果当前参数不符合规则的正则表达式则会拦截当前请求

        Param param = new Param();
        param.setId(id);
        param.setInteger(integer);
        param.setRange(range);
        param.setTime(time);
        param.setReg(reg);
        param.setNotNull(notNull);
        return ResponseEntity.build(param);

    }


    /**
     *
     * @方法功能：	TODO    这里演示了以实体封装类进行传参
     * @编写时间：	2018/3/25 17:21
     * @author：	黄先国 | hsamgle@qq.com
     * * @param param
     * @return     ResponseEntity
     */
    @RequestMapping(value = "params",method = RequestMethod.GET)
    public ResponseEntity parmsEntity(@ParamsValid(
            isEntity = true,        // 告诉参数校验器当前的参数为 实体类型
            exclude = "exclude",    // 在做参数校验的时候，把用户传进来的参数过滤掉，目的是为了不让这些参数进入业务层
            needArgs = "need"       // 在参数校验的时候，指定需要某些域的值，如果指定的域为空是，则会拦截请求并报参数缺失
    ) Param param){

        return ResponseEntity.build(param);
    }



}
