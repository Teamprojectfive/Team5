package com.korea.Team5;

import com.korea.Team5.movie.Movie;
import com.korea.Team5.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller

public class MainController {

  private final MovieService movieService;


  @GetMapping
  public String root() {
    return "redirect:/main";
  }







  @GetMapping("/main")
  public String main(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
    Page<Movie> paging = this.movieService.mainList(page);
    model.addAttribute("paging", paging);
    return "main";
  }





}
