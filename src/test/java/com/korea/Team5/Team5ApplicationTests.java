package com.korea.Team5;

import com.korea.Team5.movie.Movie;
import com.korea.Team5.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class Team5ApplicationTests {
	@Autowired
	private MovieRepository movieRepository;


	@Test
	void contextLoads() {
		Movie m = new Movie();
		m.setName("영화제목1");
		m.setContent("영화설명1");
		m.setMovieRelease("2013-12-13");
		this.movieRepository.save(m);
	}

}
