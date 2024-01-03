package com.korea.Team5.testMovieList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieListRepository extends JpaRepository<TestMovieList, Long> {
    TestMovieList findByMovieNm(String movieNm);
}
