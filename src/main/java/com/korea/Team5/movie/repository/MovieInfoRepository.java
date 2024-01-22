package com.korea.Team5.movie.repository;


import com.korea.Team5.movie.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Integer> {



    List<MovieInfo> findByMovieNmContaining(String movieNm);


    MovieInfo findByMovieCd(String movieCd);



}
