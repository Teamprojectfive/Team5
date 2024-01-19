package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findAll(Pageable pageable);

    Movie getMovieByMovieCd(String movieCd);


    List<Movie> findAllByOrderByAudiAccDesc();

    Movie findByMovieCd(String movieCd);


}

