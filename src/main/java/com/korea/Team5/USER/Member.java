package com.korea.Team5.USER;
import com.korea.Team5.Review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

  @Column(unique = true)
  private String phone;

  private String email;

  // 소셜 로그인 정보
  private String socialProvider;  // Google, Kakao, Naver 등









  @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
  private List<Review> reviewList;


}
