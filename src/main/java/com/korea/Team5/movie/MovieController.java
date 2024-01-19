package com.korea.Team5.movie;

import com.korea.Team5.Review.Review;
import com.korea.Team5.Review.ReviewService;
import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.kmapi.KmapiService;
import com.korea.Team5.movie.entity.Genre;
import com.korea.Team5.movie.entity.GenreMovieInfo;
import com.korea.Team5.movie.entity.Movie;
import com.korea.Team5.movie.entity.MovieInfo;
import lombok.RequiredArgsConstructor;
import org.ietf.jgss.GSSName;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;
    private final MemberService memberService;
    private final KmapiService kmapiService;
    private final ReviewService reviewService;


    @GetMapping("/list")
    public String list(Model model) {

        List<Movie> movieList = this.movieService.list();


        int gap = 10;
        int start = 0;
        int end = start + gap;
        int displayCount = 20;
        int subListCount = displayCount / gap;
        List<MovieInfo> movieInfo = this.movieService.infoList();
        List<List<Movie>> movieSubListList = new ArrayList<List<Movie>>();
        for (int i = 0; i < subListCount; i++) {
            movieSubListList.add(movieList.subList(start, end));
            start = end;
            end = end + gap;
        }
        model.addAttribute("movieInfo", movieInfo);
        model.addAttribute("movieList", movieList);
        model.addAttribute("movieSubList", movieSubListList);


        return "movieList";
    }

    @GetMapping("/mainList")
    public String test(Model model, @RequestParam(required = false) Integer genreId) {
        List movieList = new ArrayList<>();
        if(genreId != null){
            List<GenreMovieInfo> genreMovieInfoList = this.movieService.getMoviesByGenreId(genreId);
             movieList = this.movieService.genreMovieInfoToMovieInfo(genreMovieInfoList);
        } else {
            movieList = this.movieService.infoList();
        }

        model.addAttribute("movieList", movieList);

        List<Movie> movie = this.movieService.list();
        List<List<MovieInfo>> movieSubListList = new ArrayList<List<MovieInfo>>();
        List<Genre> genres = this.movieService.genreList();


        model.addAttribute("genres", genres);
        model.addAttribute("movie", movie);
        model.addAttribute("movieSubList", movieSubListList);
        return "mainList";
    }

    @GetMapping("/test1")
    public String test1(Model model) {
        List<Genre> genres = this.movieService.genreList();
        model.addAttribute("genres", genres);
        return "test1";
    }


    @GetMapping("/detail/{id}")
    public String detail(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @PathVariable("id") Integer id) {
        Movie movie = this.movieService.getMovie(id);
        MovieInfo movieInfo = this.movieService.getMovieInfo(id);
        Page<Review> paging = this.reviewService.getList(page);
//        List<MovieInfoDto> movieInfoList = this.kmapiService.videoListSaveDataBase();
        model.addAttribute("movieInfo", movieInfo);
//        model.addAttribute("movieInfoList", movieInfoList);
        model.addAttribute("movie", movie);
        model.addAttribute("paging", paging);


        return "movieDetail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String movieVote(Principal principal, @PathVariable("id") Integer id) {
        Movie movie = this.movieService.getMovie(id);
        Member member = this.memberService.getMember(principal.getName());

        this.movieService.vote(movie, member);

        return "redirect:/movie/list";
    }

    @GetMapping("/check")
    public String checkDataBase() {
        return "movie";
    }


    @GetMapping("/addDetail")
    public String addDetail(Model model) {
        MovieInfo movieInfo = this.movieService.getMovieDetail();
        model.addAttribute("movieInfo", movieInfo);
        return "movie";
    }

    @GetMapping("/testDetail")
    public String testDetail() {
        return "testDetail";
    }


    // api 데이터 넣는 메서드
    @GetMapping("/weeklyMovie")
    public String getMovies(@RequestParam(name = "key") String apiKey, Model model, String targetDt) {
        // weeklyMovie인 경우 기본값 설정
        if (apiKey == null) {
            apiKey = "ed496e700881942361ed983f38957c9a";
        }
        if (targetDt == null) {
            targetDt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        }
        List<Movie> movies = this.movieService.fetchDataAndSaveToDatabase(targetDt);

        model.addAttribute("movies", movies);


        return "movie";
    }


    @GetMapping("/intro")
    public String movieintro() {
        return "movie_intro";
    }


}






