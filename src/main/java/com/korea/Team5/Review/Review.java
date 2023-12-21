package com.korea.Team5.Review;
import com.korea.Team5.USER.Member;
import com.korea.Team5.movie.Movie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Getter
@Setter
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;

  private String subject;//제목

  private String content;//내용


  private String rating;//평점

  private String starRating;//별점

  private LocalDateTime createDate;//등록시간

  @ManyToOne
  private Member member;//사용자와연결

  @ManyToMany
  Set<Member> voter;

  @ManyToOne
  private User user;//소셜사용자와연결


  @ManyToOne
  private Movie movie;//무비와연결

  private LocalDateTime modifyDate;//수정일시
}
