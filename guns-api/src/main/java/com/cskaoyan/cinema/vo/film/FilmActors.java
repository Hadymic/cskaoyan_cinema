package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmActors implements Serializable {
    private static final long serialVersionUID = -6884213629247616497L;
    private String biography;
    private Actors actors;
}
