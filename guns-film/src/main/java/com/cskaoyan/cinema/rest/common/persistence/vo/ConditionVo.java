package com.cskaoyan.cinema.rest.common.persistence.vo;

import com.cskaoyan.cinema.rest.common.persistence.model.CatDictT;
import com.cskaoyan.cinema.rest.common.persistence.model.SourceDictT;
import com.cskaoyan.cinema.rest.common.persistence.model.YearDictT;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConditionVo implements Serializable {

    private static final long serialVersionUID = 7279749375889906105L;

    private List<CatInfoVo> catInfo;
    private List<SourceInfoVo> sourceInfo;
    private List<YearInfoVo> yearInfo;

    public ConditionVo() {
    }

    public ConditionVo(List<CatInfoVo> catInfo, List<SourceInfoVo> sourceInfo, List<YearInfoVo> yearInfo) {
        this.catInfo = catInfo;
        this.sourceInfo = sourceInfo;
        this.yearInfo = yearInfo;
    }
}
