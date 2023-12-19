package com.korea.Team5.Review;

import com.korea.Team5.USER.Member;
import com.korea.Team5.movie.Movie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;


  private String subject;//제목

  private String content;//내용

  private String voter;//추천

  private String rating;//평점

  private String starRating;//별점

  private LocalDateTime createDate;//등록시간
  @ManyToOne
  private Member member;

  @ManyToOne
  private Movie movie;
}