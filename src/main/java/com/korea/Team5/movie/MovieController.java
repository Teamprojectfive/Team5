package com.korea.Team5.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movie")
    public String movie(Model model){
        List<Movie> movieList = this.movieService.list();
        model.addAttribute("movieList", movieList);
        return "movie";
    }


}
