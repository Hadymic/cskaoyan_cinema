package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConditionListVo implements Serializable {

    private static final long serialVersionUID = 7279749375889906105L;

    private List<CatInfoVo> catInfo;
    private List<SourceInfoVo> sourceInfo;
    private List<YearInfoVo> yearInfo;

    public ConditionListVo() {
    }

    public ConditionListVo(List<CatInfoVo> catInfo, List<SourceInfoVo> sourceInfo, List<YearInfoVo> yearInfo) {
        this.catInfo = catInfo;
        this.sourceInfo = sourceInfo;
        this.yearInfo = yearInfo;
    }
}
