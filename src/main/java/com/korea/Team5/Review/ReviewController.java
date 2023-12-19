package com.korea.Team5.Review;


import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.movie.Movie;
import com.korea.Team5.movie.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final MemberService memberService;



    @GetMapping("/create/{id}")
    public String createReview(Model model,@PathVariable("id") Integer movieId , ReviewForm reviewForm){
      Movie movie = movieService.getMovie(movieId);
      model.addAttribute("movie", movie);

      return "/Review/review_form";
    }
    @PostMapping("/create/{id}")
    public String createReview(Model model, @PathVariable("id") Integer  movieId ,
                               @Valid ReviewForm reviewForm, BindingResult bindingResult,Principal principal){
      Movie movie = this.movieService.getMovie(movieId);
      Member member = this.memberService.getMember(principal.getName());
      if (bindingResult.hasErrors()) {
        model.addAttribute("movie", movie);
        model.addAttribute("member",member);
        return "/Review/review_form";
      }
      this.reviewService.create(movie,reviewForm.getSubject(),reviewForm.getContent(),reviewForm.getVoter(),reviewForm.getRating(),reviewForm.getStarRating(),
              member);

      return String.format("redirect:/movie/detail/%s", movieId);

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
  public String reviewModify(ReviewForm reviewForm, @PathVariable("id")Integer id, Principal principal){
      Review review = this.reviewService.getReview(id);
      if(!review.getMember().getLoginId().equals(principal.getName())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
      }
      reviewForm.setSubject(review.getSubject());
      reviewForm.setContent(review.getContent());
      reviewForm.setVoter(review.getVoter());
      reviewForm.setRating(review.getRating());
      reviewForm.setStarRating(review.getStarRating());


      return "/Review/reviewModify_form";
    }


      @PreAuthorize("isAuthenticated()")
      @PostMapping("/modify/{id}")
      public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult,
                             @PathVariable("id")Integer id,Principal principal){
      if (bindingResult.hasErrors()){
        return "/Review/reviewModify_form";
      }
      Review review = this.reviewService.getReview(id);
      if(!review.getMember().getLoginId().equals(principal.getName())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
      }
      this.reviewService.modify(review,reviewForm.getSubject(),reviewForm.getContent(),reviewForm.getVoter(),
              reviewForm.getRating(),reviewForm.getStarRating());
              return String.format("redirect:/movie/detail/%s",review.getMovie().getId());
    }



}
