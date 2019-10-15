package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.rest.common.persistence.model.FilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.rest.common.persistence.vo.FilmInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author jszza
 * @since 2019-10-14
 */
public interface FilmTMapper extends BaseMapper<FilmT> {

    /**
     * 查询不同状态的电影
     * @param page
     * @param status
     * @return
     */
    List<FilmInfo> selectFilmsByStatus(Page page, @Param("status") Integer status);

     /** Zeng-jz
     * @param sourceId
     * @param yearId
     * @param film_catId
     * @param showType
     * @return
     */
    List<FilmT> selectByIds(@Param("sourceId") int sourceId, @Param("yearId") int yearId,
                            @Param("catId") String film_catId,@Param("status") int showType);

}
