package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
@Data
public class FilmMsgVo implements Serializable {
    private static final long serialVersionUID = 361783078783435977L;
    String actors;
   String filmCats;
   List<FilmFields> filmFields;
    private   Integer filmId;
    private   String filmLength;
    private   String filmName;
    private   String filmType;
    private   String imgAddress;
    private  String  filmLanguage;
}
