package com.korea.Team5.movie;

import com.korea.Team5.Review.ReviewForm;
import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final MemberService memberService;



    @GetMapping("/list")
    public String list(Model model) {

        List<Movie> movieList = this.movieService.list();



        int gap = 20;
        int start = 0;
        int end = start + gap;
        int displayCount = 100;
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


        return "movieList";
    }




    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {

        Movie movie = this.movieService.getMovie(id);
        model.addAttribute("movie", movie);

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/vote/{id}")
    public String voteCancel(Principal principal, @PathVariable("id") Integer id){
        Movie movie = this.movieService.getMovie(id);
        Member member = this.memberService.getMember(principal.getName());
        this.movieService.voteCancle(movie, member);
        return "redirect:/movie/list";
    }


}

