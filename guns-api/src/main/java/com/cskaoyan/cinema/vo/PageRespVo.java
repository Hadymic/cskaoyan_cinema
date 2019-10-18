package com.cskaoyan.cinema.vo;

import lombok.Data;

/**
 * 带分页的返回vo
 */
@Data
public class PageRespVo<T> extends BaseRespVo<T> {
    private static final long serialVersionUID = -1298459498394790448L;

    private int nowPage;
    private int totalPage;

    public PageRespVo() {
    }

    public PageRespVo(int status, T data, String msg, int nowPage, int totalPage) {
        super(status, data, msg);
        this.nowPage = nowPage;
        this.totalPage = totalPage;
    }
}
