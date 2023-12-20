package com.korea.Team5.Review;


import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.movie.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

      private final ReviewRepository reviewRepository;


      public void create(Movie movie, String subject, String content, String rating,String starRating, Member member){

        Review review= new Review();
        review.setSubject(subject);
        review.setContent(content);
        review.setCreateDate(LocalDateTime.now());
        review.setRating(rating);
        review.setStarRating(starRating);
        review.setMovie(movie);
        review.setMember(member);
        this.reviewRepository.save(review);

  }
      public Review getReview(Integer id){
        Optional<Review> review = this.reviewRepository.findById(id);
        if(review.isPresent()){
          return review.get();
        }else {
          throw new DataNotFoundException("review not found");
        }
      }

      public void modify(Review review,String subject,String content,String rating,String starRating){
        review.setSubject(subject);
        review.setContent(content);
        review.setRating(rating);
        review.setStarRating(starRating);
        review.setModifyDate(LocalDateTime.now());
        this.reviewRepository.save(review);
      }

      public void delete(Review review){
        this.reviewRepository.delete(review);
      }


    public void vote(Review review, Member member){
      review.getVoter().add(member);
      this.reviewRepository.save(review);
    }



}
