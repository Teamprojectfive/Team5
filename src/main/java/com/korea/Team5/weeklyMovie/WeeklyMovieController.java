package com.korea.Team5.weeklyMovie;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller


@RequiredArgsConstructor
public class WeeklyMovieController {

    private final WeeklyMovieService weeklyMovieService;



    @GetMapping("/weeklyMovie")
    public String getMovies(@RequestParam String targetDt, Model model) {
        List<WeeklyMovie> movies = this.weeklyMovieService.fetchDataAndSaveToDatabase(targetDt);
        model.addAttribute("movies", movies);
        return "apiMovie";
    }



}



