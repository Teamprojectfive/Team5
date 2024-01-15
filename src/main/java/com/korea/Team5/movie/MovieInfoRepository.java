package com.korea.Team5.movie;


import com.korea.Team5.kmapi.MovieInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Integer> {


    Optional<MovieInfo> findByMovieNm(String title);


    MovieInfo findByMovieCd(String movieCd);


}
