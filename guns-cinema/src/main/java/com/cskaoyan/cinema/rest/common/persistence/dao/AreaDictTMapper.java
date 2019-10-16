package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.AreaDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.cinema.AreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 地域信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-14
 */
public interface AreaDictTMapper extends BaseMapper<AreaDictT> {

    List<AreaVo> selectListByUUID(@Param("areaId") Integer areaId);
}
