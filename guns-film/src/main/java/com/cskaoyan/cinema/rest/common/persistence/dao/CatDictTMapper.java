package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.CatDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.rest.common.persistence.vo.CatInfoVo;

import java.util.List;

/**
 * <p>
 * 类型信息表 Mapper 接口
 * </p>
 *
 * @author jszza
 * @since 2019-10-14
 */
public interface CatDictTMapper extends BaseMapper<CatDictT> {

    List<CatInfoVo> selectAll();
}
