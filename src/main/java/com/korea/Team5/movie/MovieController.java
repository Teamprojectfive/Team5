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
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final KmapiService kmapiService;
    private final ReviewService reviewService;


    @GetMapping("/boxOffice")
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


        return "boxOffice";
    }
    @PostMapping("/saveSelectedGenre")
    @ResponseBody
    public ResponseEntity<String> saveSelectedGenre(@RequestParam("genreId") String genreId, HttpSession session) {
        // 세션에 선택한 장르 정보 저장
        session.setAttribute("selectedGenreId", genreId);

        return ResponseEntity.ok("Selected genre saved to session");
    }
    @GetMapping("/top100")
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
        return "top100";
    }


    @GetMapping("/detail/{id}")
    public String detail(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @PathVariable("id") Integer id) {


        List<Movie> movies = this.movieService.list();

        Movie movie = this.movieService.getMovie(id);

        MovieInfo movieInfo = this.movieService.getMovieInfo(id);
        Page<Review> paging = this.reviewService.getList(page);
        String audiAcc = movie.getAudiAcc();

        model.addAttribute("audiAcc", audiAcc);


        model.addAttribute("movieInfo", movieInfo);
        model.addAttribute("movies", movies);
        model.addAttribute("movie", movie);
        model.addAttribute("paging", paging);


        return "movieDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String movieVote(Principal principal, @PathVariable("id") Integer id){
        MovieInfo movie = this.movieService.getMovieInfo(id);
        Member member = this.memberService.getMember(principal.getName());
        this.movieService.vote(movie, member);

        return String.format("redirect:/member/mypage");

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote2/{id}")
    public String movieInfoVote(Principal principal, @PathVariable("id") Integer id){
        MovieInfo movieInfo = this.movieService.getMovieInfo(id);
        Member member = this.memberService.getMember(principal.getName());
        this.movieService.vote(movieInfo, member);

        return String.format("redirect:/movie/top100List");
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




    @GetMapping("/search")
    public String moviesearch(Model model,@RequestParam String enterMovie){

        List<MovieInfo> searchMovieList = this.movieService.getMovieInfoNm(enterMovie);
        model.addAttribute("movieList",searchMovieList);


        return "top100";
    }

}