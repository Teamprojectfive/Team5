package com.korea.Team5.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;



    @GetMapping("/list")
    public String list(Model model){
        List<Movie> movieList = this.movieService.list();
        model.addAttribute("movieList", movieList);
        return "movieList";
    }
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id){
        Movie movie = this.movieService.getMovie(id);


        model.addAttribute("movie", movie);


        return "movieDetail";
    }





}
