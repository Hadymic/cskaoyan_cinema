package com.cskaoyan.cinema.rest.modular.auth.validator.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.UserTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.UserT;
import com.cskaoyan.cinema.rest.modular.auth.validator.IReqValidator;
import com.cskaoyan.cinema.rest.modular.auth.validator.dto.Credence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账号密码验证
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    private UserTMapper userTMapper;

    @Override
    public boolean validate(Credence credence) {
        List<UserT> users = userTMapper.selectList(new EntityWrapper<UserT>().eq("user_name", credence.getCredenceName()));
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
