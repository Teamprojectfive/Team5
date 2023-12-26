package com.korea.Team5.Api;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Controller


@RequiredArgsConstructor
public class DailyMovieController {

    private final DailyMovieService dailyMovieService;



    @GetMapping("/dailyMovie")
    public String getMovies(@RequestParam String targetDt, Model model) {
        List<DailyMovie> movies = this.dailyMovieService.fetchDataAndSaveToDatabase(targetDt);
        model.addAttribute("movies", movies);
        return "apiMovie";
    }
}



