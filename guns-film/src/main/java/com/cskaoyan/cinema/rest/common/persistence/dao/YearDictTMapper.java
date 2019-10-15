package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.YearDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.rest.common.persistence.vo.YearInfoVo;

import java.util.List;

/**
 * <p>
 * 年代信息表 Mapper 接口
 * </p>
 *
 * @author jszza
 * @since 2019-10-14
 */
public interface YearDictTMapper extends BaseMapper<YearDictT> {

    List<YearInfoVo> selectAll();
}
