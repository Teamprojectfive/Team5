package com.korea.Team5.movie;

import com.korea.Team5.Review.Review;
import com.korea.Team5.Review.ReviewService;
import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;
    private final MemberService memberService;

    private final ReviewService reviewService;



    @GetMapping("/list")
    public String list(Model model) {

        List<Movie> movieList = this.movieService.list();


        int gap = 10;
        int start = 0;
        int end = start + gap;
        int displayCount = 120;
        int subListCount = displayCount / gap;

        List<List<Movie>> movieSubListList = new ArrayList<List<Movie>>();
        for (int i = 0; i < subListCount; i++) {
            movieSubListList.add(movieList.subList(start, end));
            start = end;
            end = end + gap;
        }

        model.addAttribute("movieList", movieList);
        model.addAttribute("movieSubList", movieSubListList);


        return "movieList";
    }




    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(value="page", defaultValue="0") int page , @RequestParam String movieCd) {

        Page<Review> paging = this.reviewService.getList(page);
        MovieInfo movieInfo = this.movieService.getMovieDetail(movieCd);

        model.addAttribute("movieInfo", movieInfo);
        model.addAttribute("paging",paging);

        return "movieDetail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String movieVote(Principal principal, @PathVariable("id") Integer id){
        Movie movie = this.movieService.getMovie(id);
        Member member = this.memberService.getMember(principal.getName());

        this.movieService.vote(movie, member);

        return "redirect:/movie/list";
    }

    public String getDefaultDate() {
        // 예시로 현재 날짜를 기본값으로 설정하고자 할 때
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }



    // api 데이터 넣는 메서드
    @GetMapping("/weeklyMovie")
    public String getMovies(@RequestParam(name = "key") String apiKey,
                            @RequestParam(name = "targetDt") String targetDt,
                            Model model) {
        // weeklyMovie인 경우 기본값 설정
        if (apiKey == null) {
            apiKey = "ed496e700881942361ed983f38957c9a";
        }
        if (targetDt == null) {
            targetDt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        List<Movie> movies = this.movieService.fetchDataAndSaveToDatabase(targetDt);
        model.addAttribute("movies", movies);

        return "apiMovie";
    }

    @GetMapping("/addDetail")
    public String addDetail(@RequestParam String movieCd, Model model){
        MovieInfo movieInfo = this.movieService.getMovieDetail(movieCd);
        model.addAttribute("movieInfo", movieInfo);
        return "addDetail";
    }

    @GetMapping("/testDetail")
    public String testDetail(){
        return "testDetail";
    }




    @GetMapping("/intro")
    public String movieintro() {
        return "movie_intro";
    }
    @GetMapping("/mainlist")
    public String mainList(Model model) {

        List<Movie> movieList = this.movieService.list();

        int gap = 10;
        int start = 0;
        int end = start + gap;
        int displayCount = 120;
        int subListCount = displayCount / gap;

        List<List<Movie>> movieSubListList = new ArrayList<List<Movie>>();
        for (int i = 0; i < subListCount; i++) {
            movieSubListList.add(movieList.subList(start, end));
            start = end;
            end = end + gap;
        }

        model.addAttribute("movieList", movieList);
        model.addAttribute("movieSubList", movieSubListList);
        model.addAttribute("movieList", movieList);


        return "mainList";
    }
    // api 데이터 넣는 메서드

    }





