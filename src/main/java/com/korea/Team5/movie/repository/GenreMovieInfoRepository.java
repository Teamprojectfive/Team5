package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.Genre;
import com.korea.Team5.movie.entity.GenreMovieInfo;
import com.korea.Team5.movie.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreMovieInfoRepository extends JpaRepository<GenreMovieInfo, Integer> {
    GenreMovieInfo findByGenreAndMovieInfo(Genre genre, MovieInfo movieInfo);

    List<GenreMovieInfo> findByGenreId(Integer genreId);
}
