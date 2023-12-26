package com.korea.Team5.MovieApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/api")
    public String getMovies(@RequestParam String targetDt){
        return apiService.getData(targetDt);
    }



}
