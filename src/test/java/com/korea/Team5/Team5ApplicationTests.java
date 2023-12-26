package com.korea.Team5;

import com.korea.Team5.Review.ReviewService;
import com.korea.Team5.movie.Movie;
import com.korea.Team5.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class Team5ApplicationTests {
	@Autowired
	private MovieRepository movieRepository;

	private ReviewService reviewService;



	@Test
	void contextLoads() {
		Movie m = new Movie();

		m.setName("터미네이터");
		m.setContent("영화설명1");
		m.setMovieRelease("1984-12-22");
		m.setGenre("액션");
		m.setCast("정우성");
		m.setImage("https://movie-phinf.pstatic.net/20191030_118/1572411669676j0Arb_JPEG/movie_image.jpg");
		this.movieRepository.save(m);

		Movie m2 = new Movie();
		m2.setName("영화제목2");
		m2.setContent("영화설명2");
		m2.setMovieRelease("2013-12-13");

		this.movieRepository.save(m2);

		Movie m3 = new Movie();
		m3.setName("영화제목3");
		m3.setContent("영화설명3");
		m3.setMovieRelease("2013-12-13");
		this.movieRepository.save(m3);

		Movie m4 = new Movie();
		m4.setName("영화제목4");
		m4.setContent("영화설명4");
		m4.setMovieRelease("2013-12-13");

		this.movieRepository.save(m4);

		Movie m5 = new Movie();
		m5.setName("영화제목5");
		m5.setContent("영화설명5");
		m5.setMovieRelease("2013-12-13");
		this.movieRepository.save(m5);

		Movie m6 = new Movie();
		m6.setName("영화제목6");
		m6.setContent("영화설명6");
		m6.setMovieRelease("2013-12-13");
		this.movieRepository.save(m6);

		Movie m7 = new Movie();
		m7.setName("영화제목7");
		m7.setContent("영화설명7");
		m7.setMovieRelease("2013-12-13");
		this.movieRepository.save(m7);

		Movie m8 = new Movie();
		m8.setName("영화제목8");
		m8.setContent("영화설명8");
		m8.setMovieRelease("2013-12-13");
		this.movieRepository.save(m8);

		Movie m9 = new Movie();
		m9.setName("영화제목9");
		m9.setContent("영화설명9");
		m9.setMovieRelease("2013-12-13");
		this.movieRepository.save(m9);

		Movie m10 = new Movie();
		m10.setName("영화제목10");
		m10.setContent("영화설명10");
		m10.setMovieRelease("2013-12-13");
		this.movieRepository.save(m10);
	}



}
