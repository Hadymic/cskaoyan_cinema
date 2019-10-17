package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.BrandDictT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.cinema.BrandVo;

import java.util.List;

/**
 * <p>
 * 品牌信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-14
 */
public interface BrandDictTMapper extends BaseMapper<BrandDictT> {

    List<BrandVo> selectListByUUID(Integer brandId);
}
