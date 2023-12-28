package com.korea.Team5.testMovieList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieListController {
    private final MovieListService movieListService;
    @GetMapping("/apiMovieList")
    public String movieList(Model model){
        List<TestMovieList> movies2 = this.movieListService.movieListSaveDataBase();
        model.addAttribute("movies2", movies2);
        return "movieList";
    }
}
