package com.cskaoyan.cinema.rest.common.persistence.vo;

import com.cskaoyan.cinema.rest.common.persistence.model.BannerT;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexVO implements Serializable {
    private static final long serialVersionUID = -4899683492034322201L;

    private List<BannerT> banners;
    private HotFilms hotFilms;
    private SoonFilms soonFilms;
    private List<BoxRanking> boxRanking;
    private List<ExpectRanking> expectRankings;
    private List<Top> top100;
}
