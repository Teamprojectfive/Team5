package com.korea.Team5.Review;


import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.movie.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;


  public void create(Movie movie, String subject, String content, int starRating, Member member) {
    Review review = new Review();
    review.setSubject(subject);
    review.setContent(content);
    review.setCreateDate(LocalDateTime.now());
    review.setStarRating(starRating);
    review.setMovie(movie);
    review.setMember(member);
    this.reviewRepository.save(review);
  }


  public Review getReview(Integer id) {
    Optional<Review> review = this.reviewRepository.findById(id);
    if (review.isPresent()) {
      return review.get();
    } else {
      throw new DataNotFoundException("review not found");
    }
  }


  public void modify(Review review, String subject, String content, int starRating) {
    review.setSubject(subject);
    review.setContent(content);
    review.setStarRating(starRating);
    review.setModifyDate(LocalDateTime.now());
    this.reviewRepository.save(review);
  }


  public void delete(Review review) {
    this.reviewRepository.delete(review);
  }


  public boolean vote(Review review, Member member) {
    Set<Member> voters = review.getVoter();

    // 이미 추천한 상태이면 취소하고 false 반환
    if (voters.contains(member)) {
      voters.remove(member);
      this.reviewRepository.save(review);
      return false;
    }
    // 추천되지 않은 상태이면 추가하고 true 반환
    voters.add(member);
    this.reviewRepository.save(review);
    return true;

  }

  public Page<Review> getList(int page) {
    Pageable pageable = PageRequest.of(page, 10);
    return this.reviewRepository.findAll(pageable);
  }
  public Page<Review> getListReveiwMember(int page, Member member) {
    Pageable pageable = PageRequest.of(page, 10);
    return this.reviewRepository.findByMember(member, pageable);
  }

  public Review findReviewById(Integer reviewId) {
    // JpaRepository에서 제공하는 findById 메서드 활용
    return reviewRepository.findById(reviewId).orElse(null);
  }
}