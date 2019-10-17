package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmActor implements Serializable {
    private static final long serialVersionUID = -1307255471845164030L;
    private String imgAddress;
    private String directorName;
    private String roleName;

    public FilmActor(String imgAddress, String directorName) {
        this.imgAddress = imgAddress;
        this.directorName = directorName;
    }

    public FilmActor(String imgAddress, String directorName, String roleName) {
        this.imgAddress = imgAddress;
        this.directorName = directorName;
        this.roleName = roleName;
    }
}
