package com.hsamgle.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsamgle.basic.annotation.ConsumeTime;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.constant.TCode;
import com.hsamgle.dao.IUserDao;
import com.hsamgle.entity.mysql.UserInfo;
import com.hsamgle.mysql.entity.SqlCondition;
import com.hsamgle.mysql.entity.SqlOperators;
import com.hsamgle.service.inter.IUserService;
import ex.com.hsamgle.mysql.base.BaseExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.service.impl
 * @ClassName
 * @Description
 * @date 2018/3/25 17:27
 * @company 广州讯动网络科技有限公司
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    @ConsumeTime(title = "添加用户")                 //  @ConsumeTime   这个注解主要是用来记录当前方法请求所用时间，并输出到控制台
    public ResponseEntity add ( UserInfo user ) {
        userDao.insert(user);
        return ResponseEntity.build(user);
    }

    @Override
    public ResponseEntity getById ( Integer id ) {
        UserInfo userInfo = userDao.selectByPrimaryKey(id);
        return ResponseEntity.build(userInfo);
    }

    @Override
    public ResponseEntity list ( UserInfo user ){

        try {
//            PageInfo<UserInfo> pageInfo = 旧的写法(user);          //旧的写法
            PageInfo<UserInfo> pageInfo = 新的写法(user);          //新的写法
            return ResponseEntity.build(pageInfo);

        }catch (Exception e){
            e.printStackTrace();
            // 通过继承父类的code来扩展错误码和错误提示文案
            return ResponseEntity.build(TCode.SERVER_ERR,TCode.TIPS);
        }
    }


    private   PageInfo<UserInfo> 旧的写法( UserInfo user){
	    // 这种需要构造出example的写法是旧的写法
	    BaseExample example = BaseExample.getExample(UserInfo.class);

	    SqlCondition[] conditions = new SqlCondition[]{
			    new SqlCondition("userName",user.getUserName()),         // 构建 等于 条件
			    new SqlCondition("age", SqlOperators.gt,20),       // 构建大于条件
			    new SqlCondition("gender",SqlOperators.ne,0)     // 构建不等于条件
	    };
	    example.build(conditions);


	    // 这里开启夹心饼分页模式 ，写法有点蛋疼，有待优化
	    PageHelper.startPage(user.getpNow(),user.getpSize());               // startPage
	    List<UserInfo> userInfos = userDao.selectByExample(example);        // 执行查询
	    return new PageInfo<>(userInfos);
    }

	private  PageInfo<UserInfo> 新的写法(UserInfo user){
		SqlCondition[] conditions = new SqlCondition[]{
				new SqlCondition("userName",user.getUserName()),         // 构建 等于 条件
				new SqlCondition("age", SqlOperators.gt,20),       // 构建大于条件
				new SqlCondition("gender",SqlOperators.ne,0)     // 构建不等于条件
		};
		// 这里开启夹心饼分页模式 ，写法有点蛋疼，有待优化
		PageHelper.startPage(user.getpNow(),user.getpSize());               // startPage
		List<UserInfo> userInfos = userDao.selectByExample(conditions);        // 执行查询
		return new PageInfo<>(userInfos);
	}



    @Override
    public ResponseEntity update ( UserInfo user ) {
        return null;
    }

    @Override
    public ResponseEntity delete ( Integer id ) {
        return null;
    }

    @Override
    public ResponseEntity queryByMybatis ( Integer id ) {
        return null;
    }
}
