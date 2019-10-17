package com.cskaoyan.cinema.rest.common.persistence.vo;

import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;

public class OrderStatusVo {

    public static String get(int status){
        if (status == 0) {
            return "未支付";
        }else if (status == 1){
            return "已完成";
        }else if (status == 2){
            return "已关闭";
        }else {
            throw new GunsException(GunsExceptionEnum.SERVER_ERROR);
        }
    }
}
