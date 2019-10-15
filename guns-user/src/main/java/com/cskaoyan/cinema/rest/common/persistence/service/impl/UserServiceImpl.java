package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cskaoyan.cinema.core.util.MD5Util;
import com.cskaoyan.cinema.rest.common.persistence.dao.UserTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.UserT;
import com.cskaoyan.cinema.service.UserService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import com.cskaoyan.cinema.vo.user.UserVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserTMapper userTMapper;

    @Override
    public Integer register(UserRegisterVo vo) {
        UserT usert = new UserT();
        usert.setUserName(vo.getUsername());
        usert.setUserPwd(MD5Util.encrypt(vo.getPassword()));
        usert.setUserPhone(vo.getMobile());
        usert.setEmail(vo.getEmail());
        usert.setAddress(vo.getAddress());
        Date date = new Date();
        usert.setBeginTime(date);
        usert.setUpdateTime(date);
        usert.setUserSex(0);
        return userTMapper.insert(usert);
    }

    @Override
    public Integer check(String username) {
        EntityWrapper<UserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name", username);
        return userTMapper.selectCount(wrapper);
    }

    @Override
    public BaseRespVo selectUserInfo(String userId) {
        if (userId != null) {
            Integer uuid = Integer.parseInt(userId);
            UserT userT = userTMapper.selectById(uuid);
            return new BaseRespVo(0, userT, null);
        } else if (userId == null) {
            return new BaseRespVo(1, null, "查询失败，用户尚未登录");
        }
        return new BaseRespVo(999, null, "系统出现异常，请联系管理员");
    }

    @Override
    public BaseRespVo updateUserInfo(UserVo userVo) {
        userVo.setUpdateTime(new Date());//更新时间
        Integer code = userTMapper.updateUserInfo(userVo);
        if (code == 1) {//成功
            UserT userT = userTMapper.selectById(userVo.getUuid());
            userVo.setId(userT.getUuid());
            userVo.setHeadAddress(userT.getHeadUrl());
            userVo.setCreatTime(userT.getBeginTime());
            userVo.setUpdateTime(userT.getUpdateTime());
            return new BaseRespVo(0, userVo, null);
        } else if (code == 0) {//失败
            return new BaseRespVo(1, null, "用户信息修改失败");
        }
        return new BaseRespVo(999, null, "系统出现异常，请联系管理员");
    }
}
