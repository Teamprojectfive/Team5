package com.korea.Team5.movie.repository;


import com.korea.Team5.movie.entity.MovieInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Integer> {



    List<MovieInfo> findByMovieNmContaining(String movieNm);

    Page<MovieInfo> findAll(Pageable pageable);


    MovieInfo findByMovieNm(String movieNm);

    MovieInfo findByMovieCd(String movieCd);

}
