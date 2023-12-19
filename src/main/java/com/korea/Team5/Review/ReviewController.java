package com.korea.Team5.Review;


import com.korea.Team5.movie.Movie;
import com.korea.Team5.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final MovieService movieService;
    private final ReviewService reviewService;



    @GetMapping("/create/{id}")
    public String createReview(Model model,@PathVariable("id") Integer movieId){
      Movie movie = movieService.getMovie(movieId);
      model.addAttribute("movie", movie);

      return "review_form";
    }
    @PostMapping("/create/{id}")
    public String createReview(Model model, @PathVariable("id") Integer  movieId ,
                               String subject, @RequestParam(value="content") String content,
                               String voter,String rating,String starRating){
      Movie movie = this.movieService.getMovie(movieId);
      this.reviewService.create(movie,subject,content,voter,rating,starRating);

      return String.format("redirect:/movie/detail/%s", movieId);

    }





}
