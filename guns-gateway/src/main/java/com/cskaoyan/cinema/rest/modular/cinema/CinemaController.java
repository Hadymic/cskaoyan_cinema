package com.cskaoyan.cinema.rest.modular.cinema;

import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.vo.CinemaQueryVO;
import com.cskaoyan.cinema.vo.CinemaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    @Autowired
private CinemaService cinemaService;
@RequestMapping("cinema/getCinemas")

public CinemaVO  query(CinemaQueryVO cinemaQueryVO){

}
}
