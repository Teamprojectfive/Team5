package com.korea.Team5.USER;
import com.korea.Team5.Review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Setter
@Getter
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String loginId;

  private String password;

  @Column(unique = true)
  private String nickName;

  private String phone;

  private String email;

  private String name;

  private LocalDateTime createDate;


  // 소셜 로그인 정보
  private String socialProvider;  // Google, Kakao, Naver 등


  @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
  private List<Review> reviewList;

  private String role;
}
