package com.korea.Team5.Api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DailyMovieController {

    private final DailyMovieService dailyMovieService;



    @GetMapping("/dailyMovie")
    public void getMovies(@RequestParam String targetDt) {
        this.dailyMovieService.fetchDataAndSaveToDatabase(targetDt);
    }
}
