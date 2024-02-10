package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.MovieInfo;
import com.korea.Team5.movie.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Long> {
    Nation findByNationNmAndMovieInfo(String nationNm, MovieInfo targetMovieInfo);
}
