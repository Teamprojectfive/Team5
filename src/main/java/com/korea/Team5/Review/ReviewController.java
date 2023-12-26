package com.korea.Team5.Review;

import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.movie.Movie;
import com.korea.Team5.movie.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
public class ReviewController {

  private final MovieService movieService;
  private final ReviewService reviewService;
  private final MemberService memberService;


  @GetMapping("/list")
  public String list(Model model, @RequestParam(value="page", defaultValue="0") int page ,Movie movie) {
    Page<Review> paging = this.reviewService.getList(page);
    model.addAttribute("paging", paging);
    model.addAttribute("movie",movie);
    return "movieDetail";
  }

  //리뷰생성
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create/{id}")
  public String createReview(Model model, @PathVariable("id") Integer movieId, ReviewForm reviewForm,Member member) {
    Movie movie = movieService.getMovie(movieId);
    model.addAttribute("movie", movie);

    return "/Review/review_form";
  }
  //리뷰 생성
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create/{id}")
  public String createReview(Model model, @PathVariable("id") Integer movieId,
                             @Valid ReviewForm reviewForm, BindingResult bindingResult, Principal principal) {
    Movie movie = this.movieService.getMovie(movieId);
    Member member = this.memberService.getMember(principal.getName());
    if (bindingResult.hasErrors()) {
      model.addAttribute("movie", movie);
      model.addAttribute("member", member);
      return "/Review/review_form";
    }
    this.reviewService.create(movie, reviewForm.getSubject(), reviewForm.getContent(), reviewForm.getRating(), reviewForm.getStarRating(),
            member);

    return String.format("redirect:/movie/detail/%s", movieId);

  }
  //리뷰삭제
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String reviewDelete(Principal principal, @PathVariable("id") Integer id) {
    Review review = this.reviewService.getReview(id);
    if (!review.getMember().getLoginId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }
    this.reviewService.delete(review);
    return String.format("redirect:/movie/detail/%s", review.getMovie().getId());
  }

  //리뷰추천
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String reviewVote(Principal principal, @PathVariable("id") Integer id) {

    Review review = this.reviewService.getReview(id);
    Member member = this.memberService.getMember(principal.getName());
    this.reviewService.vote(review, member);
    return String.format("redirect:/movie/detail/%s", review.getMovie().getId());
  }


  //리뷰수정
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String reviewModify(ReviewForm reviewForm, @PathVariable("id") Integer id, Principal principal) {
    Review review = this.reviewService.getReview(id);
    if (!review.getMember().getLoginId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    reviewForm.setSubject(review.getSubject());
    reviewForm.setContent(review.getContent());
    reviewForm.setRating(review.getRating());
    reviewForm.setStarRating(review.getStarRating());


    return "/Review/reviewModify_form";
  }

  //리뷰수정
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult,
                             @PathVariable("id") Integer id, Principal principal) {
    if (bindingResult.hasErrors()) {
      return "/Review/reviewModify_form";
    }
    Review review = this.reviewService.getReview(id);
    if (!review.getMember().getLoginId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    this.reviewService.modify(review, reviewForm.getSubject(), reviewForm.getContent(),
            reviewForm.getRating(), reviewForm.getStarRating());
    return String.format("redirect:/movie/detail/%s", review.getMovie().getId());
  }


}
