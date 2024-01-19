package com.korea.Team5;

import com.korea.Team5.Review.ReviewService;
import com.korea.Team5.movie.repository.MovieRepository;
import com.korea.Team5.theater.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest

class Team5ApplicationTests {
	@Autowired
	private MovieRepository movieRepository;

	private ReviewService reviewService;

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private SmallRegionRepository smallRegionRepository;



//	@Test
//	void contextLoads() {
//		Movie m = new Movie();
//
//		m.setName("터미네이터");
//		m.setContent("영화설명1");
//		m.setMovieRelease("1984-12-22");
//		m.setGenre("액션");
//		m.setCast("정우성");
//		m.setImage("https://movie-phinf.pstatic.net/20191030_118/1572411669676j0Arb_JPEG/movie_image.jpg");
//		this.movieRepository.save(m);
//
//		Movie m2 = new Movie();
//		m2.setName("영화제목2");
//		m2.setContent("영화설명2");
//		m2.setMovieRelease("2013-12-13");
//
//		this.movieRepository.save(m2);
//
//		Movie m3 = new Movie();
//		m3.setName("영화제목3");
//		m3.setContent("영화설명3");
//		m3.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m3);
//
//		Movie m4 = new Movie();
//		m4.setName("영화제목4");
//		m4.setContent("영화설명4");
//		m4.setMovieRelease("2013-12-13");
//
//		this.movieRepository.save(m4);
//
//		Movie m5 = new Movie();
//		m5.setName("영화제목5");
//		m5.setContent("영화설명5");
//		m5.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m5);
//
//		Movie m6 = new Movie();
//		m6.setName("영화제목6");
//		m6.setContent("영화설명6");
//		m6.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m6);
//
//		Movie m7 = new Movie();
//		m7.setName("영화제목7");
//		m7.setContent("영화설명7");
//		m7.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m7);
//
//		Movie m8 = new Movie();
//		m8.setName("영화제목8");
//		m8.setContent("영화설명8");
//		m8.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m8);
//
//		Movie m9 = new Movie();
//		m9.setName("영화제목9");
//		m9.setContent("영화설명9");
//		m9.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m9);
//
//		Movie m10 = new Movie();
//		m10.setName("영화제목10");
//		m10.setContent("영화설명10");
//		m10.setMovieRelease("2013-12-13");
//		this.movieRepository.save(m10);
//
//
//	}
	@Test
	void contextLoads1() {

		Theater t1 = new Theater();
		t1.setName("극장이름1");
		t1.setBusinessname("사업회사 명1");
		t1.setAddress("극장주소1");
		this.theaterRepository.save(t1);

		Theater t2 = new Theater();
		t2.setName("극장이름2");
		t2.setBusinessname("사업회사 명2");
		t2.setAddress("극장주소2");
		this.theaterRepository.save(t2);

		Theater t3 = new Theater();
		t3.setName("극장이름3");
		t3.setBusinessname("사업회사 명3");
		t3.setAddress("극장주소3");
		this.theaterRepository.save(t3);

		Theater t4 = new Theater();
		t4.setName("극장이름4");
		t4.setBusinessname("사업회사 명4");
		t4.setAddress("극장주소4");
		this.theaterRepository.save(t4);

		Theater t5 = new Theater();
		t5.setName("극장이름5");
		t5.setBusinessname("사업회사 명5");
		t5.setAddress("극장주소5");
		this.theaterRepository.save(t5);

		Theater t6 = new Theater();
		t6.setName("극장이름6");
		t6.setBusinessname("사업회사 명6");
		t6.setAddress("극장주소6");
		this.theaterRepository.save(t6);

		Theater t7 = new Theater();
		t7.setName("극장이름7");
		t7.setBusinessname("사업회사 명7");
		t7.setAddress("극장주소7");
		this.theaterRepository.save(t7);

		Theater t8 = new Theater();
		t8.setName("극장이름8");
		t8.setBusinessname("사업회사 명8");
		t8.setAddress("극장주소8");
		this.theaterRepository.save(t8);


		Region r1 = new Region();
		r1.setName("서울시");
		this.regionRepository.save(r1);

		Region r2 = new Region();
		r2.setName("부산시");
		this.regionRepository.save(r1);

		SmallRegion sr1 = new SmallRegion();
		sr1.setName("강남구");
		this.smallRegionRepository.save(sr1);

		SmallRegion sr2 = new SmallRegion();
		sr2.setName("강남구");
		this.smallRegionRepository.save(sr1);

	}




}
