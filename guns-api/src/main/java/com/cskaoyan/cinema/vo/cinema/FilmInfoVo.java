package com.cskaoyan.cinema.vo.cinema;

import com.cskaoyan.cinema.vo.cinema.FilmFields;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmInfoVo implements Serializable {
    private static final long serialVersionUID = 649739984670444509L;
    private String filmId;
    private String filmName;
    private String filmType;
    private String imgAddress;
    private String filmCats;
    //private String filmLength;
    private String actors;
    private String imdAddress;
    private List filmFieldVos;
}
