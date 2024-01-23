package com.korea.Team5;
import com.korea.Team5.Review.ReviewService;
<<<<<<< HEAD
<<<<<<< HEAD
import com.korea.Team5.movie.entity.Movie;
import com.korea.Team5.movie.repository.MovieRepository;
<<<<<<< HEAD
=======
import com.korea.Team5.movie.MovieRepository;
=======
import com.korea.Team5.movie.repository.MovieRepository;
>>>>>>> 5ce3ecd66cc2cf22fc01c596d358f70438ff0f98
=======
import com.korea.Team5.movie.entity.Movie;
import com.korea.Team5.movie.repository.MovieRepository;
>>>>>>> ac8ad278f13fae1ed7945e7e53574153dfac35a5
import com.korea.Team5.theater.*;
>>>>>>> fa39e7d40445b183bac1108d138825ff9b7fc048
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


//<<<<<<< HEAD
////<<<<<<< HEAD
//////package com.korea.Team5;
//////
//////import com.korea.Team5.Review.ReviewService;
//////import com.korea.Team5.movie.Movie;
//////import com.korea.Team5.movie.MovieRepository;
//////import org.junit.jupiter.api.Test;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.boot.test.context.SpringBootTest;
//////
//////@SpringBootTest
//////
//////class Team5ApplicationTests {
//////	@Autowired
//////	private MovieRepository movieRepository;
//////
//////	private ReviewService reviewService;
//////
//////
//////
////=======
////package com.korea.Team5;
//=======
//package com.korea.Team5;





//	@Test
//	void contextLoads() {
//		Movie m = new Movie();

//
//import com.korea.Team5.Review.ReviewService;
//import com.korea.Team5.movie.repository.MovieRepository;
//import com.korea.Team5.theater.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//@SpringBootTest
//
//class Team5ApplicationTests {
//	@Autowired
//	private MovieRepository movieRepository;
//
//	private ReviewService reviewService;
//
//	@Autowired
//	private TheaterRepository theaterRepository;
//
//	@Autowired
//	private RegionRepository regionRepository;
//
//	@Autowired
//	private SmallRegionRepository smallRegionRepository;
//
//
//
////	@Test
////	void contextLoads() {
////		Movie m = new Movie();
//>>>>>>> 5ce3ecd66cc2cf22fc01c596d358f70438ff0f98
////
////import com.korea.Team5.Review.ReviewService;
////import com.korea.Team5.movie.MovieRepository;
////import com.korea.Team5.theater.*;
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
////
////@SpringBootTest
////
////class Team5ApplicationTests {
////	@Autowired
////	private MovieRepository movieRepository;
////
////	private ReviewService reviewService;
////
////	@Autowired
////	private TheaterRepository theaterRepository;
////
////	@Autowired
////	private RegionRepository regionRepository;
////
////	@Autowired
////	private SmallRegionRepository smallRegionRepository;
////
////
////
////>>>>>>> 86bbbfbc1c67affbda4ed9f4c645fb3b0e759352
//////	@Test
//////	void contextLoads() {
//////		Movie m = new Movie();
//////
//////		m.setName("터미네이터");
//////		m.setContent("영화설명1");
//////		m.setMovieRelease("1984-12-22");
//////		m.setGenre("액션");
//////		m.setCast("정우성");
//////		m.setImage("https://movie-phinf.pstatic.net/20191030_118/1572411669676j0Arb_JPEG/movie_image.jpg");
//////		this.movieRepository.save(m);
//////
//////		Movie m2 = new Movie();
//////		m2.setName("영화제목2");
//////		m2.setContent("영화설명2");
//////		m2.setMovieRelease("2013-12-13");
//////
//////		this.movieRepository.save(m2);
//////
//////		Movie m3 = new Movie();
//////		m3.setName("영화제목3");
//////		m3.setContent("영화설명3");
//////		m3.setMovieRelease("2013-12-13");
//////		this.movieRepository.save(m3);
//////
//////		Movie m4 = new Movie();
//////		m4.setName("영화제목4");
//////		m4.setContent("영화설명4");
//////		m4.setMovieRelease("2013-12-13");
//////
//////		this.movieRepository.save(m4);
//////
//////		Movie m5 = new Movie();
//////		m5.setName("영화제목5");
//////		m5.setContent("영화설명5");
//////		m5.setMovieRelease("2013-12-13");
//////		this.movieRepository.save(m5);
//////
//////		Movie m6 = new Movie();
//////		m6.setName("영화제목6");
////		m6.setContent("영화설명6");
////		m6.setMovieRelease("2013-12-13");
////		this.movieRepository.save(m6);
////
////		Movie m7 = new Movie();
////		m7.setName("영화제목7");
////		m7.setContent("영화설명7");
////		m7.setMovieRelease("2013-12-13");
////		this.movieRepository.save(m7);
////
////		Movie m8 = new Movie();
////		m8.setName("영화제목8");
////		m8.setContent("영화설명8");
////		m8.setMovieRelease("2013-12-13");
////		this.movieRepository.save(m8);
////
////		Movie m9 = new Movie();
////		m9.setName("영화제목9");
////		m9.setContent("영화설명9");
////		m9.setMovieRelease("2013-12-13");
////		this.movieRepository.save(m9);
////
////		Movie m10 = new Movie();
////		m10.setName("영화제목10");
////		m10.setContent("영화설명10");
////		m10.setMovieRelease("2013-12-13");
////		this.movieRepository.save(m10);
////	}
////
////
////}
//
////
////
////	}
