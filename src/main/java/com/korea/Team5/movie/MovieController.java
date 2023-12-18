package com.korea.Team5.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @GetMapping("/main")
    public String movie(Model model, @RequestParam(value="page", defaultValue="0") int page){
        Page<Movie> paging = this.movieService.list(page);
        model.addAttribute("paging", paging);
        return "main";
    }

    @GetMapping("/movie/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id){
        Movie movie = this.movieService.getMovie(id);


        model.addAttribute("movie", movie);


        return "movieDetail";
    }



}
