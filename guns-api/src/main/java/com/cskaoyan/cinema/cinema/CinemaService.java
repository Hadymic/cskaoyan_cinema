package com.cskaoyan.cinema.cinema;

<<<<<<< HEAD
import com.cskaoyan.cinema.vo.cinema.CinemaMsgVo;
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import com.cskaoyan.cinema.vo.cinema.ListBean;
=======
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
>>>>>>> 82aecced46fd54d4b0c35bdbbdc7e7d982ede52d

import java.util.List;

public interface CinemaService {
    ListBean<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);

    CinemaMsgVo queryCinemaMsg(String cinemaId);
}
