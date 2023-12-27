package com.korea.Team5.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
  Page<Review> findByMovieId(Integer movieId, Pageable pageable);
  Page<Review> findAll(Pageable pageable);
}
