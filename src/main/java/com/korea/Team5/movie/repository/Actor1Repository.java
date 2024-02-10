package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.Actor1;
import com.korea.Team5.movie.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Actor1Repository extends JpaRepository<Actor1, Long> {
    Actor1 findByPeopleNmAndMovieInfo(String peopleNm, MovieInfo targetMovieInfo);
}
