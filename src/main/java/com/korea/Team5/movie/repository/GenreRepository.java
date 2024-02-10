package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.Genre;
import com.korea.Team5.movie.entity.GenreMovieInfo;
import com.korea.Team5.movie.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {


    Genre findByGenreNm(String genreNm);
}
