package com.korea.Team5.Review;


import com.korea.Team5.movie.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {

      private final ReviewRepository reviewRepository;


      public void create(Movie movie,String subject, String content,String rating,String voter,String starRating){

        Review review= new Review();
        review.setSubject(subject);
        review.setContent(content);
        review.setCreateDate(LocalDateTime.now());
        review.setRating(rating);
        review.setVoter(voter);
        review.setStarRating(starRating);
        review.setMovie(movie);
        this.reviewRepository.save(review);

  }



}
