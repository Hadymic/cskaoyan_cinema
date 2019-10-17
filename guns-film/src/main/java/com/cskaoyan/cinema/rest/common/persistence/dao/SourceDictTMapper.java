package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.SourceDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.film.SourceInfoVo;

import java.util.List;

/**
 * <p>
 * 区域信息表 Mapper 接口
 * </p>
 *
 * @author jszza
 * @since 2019-10-14
 */
public interface SourceDictTMapper extends BaseMapper<SourceDictT> {

    List<SourceInfoVo> selectAll();
}
