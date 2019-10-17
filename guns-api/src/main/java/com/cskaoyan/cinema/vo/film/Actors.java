package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Actors implements Serializable {
    private static final long serialVersionUID = 8385216707409596302L;
    private FilmActor director;
    private List<FilmActor> actors;
}
