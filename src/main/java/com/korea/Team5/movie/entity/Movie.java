package com.korea.Team5.movie.entity;

import com.korea.Team5.Review.Review;
import com.korea.Team5.USER.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    private String rnum;  // 순번
    private String rank;  // 해당일자의 박스오피스 순위
    private String movieCd;  // 영화의 대표코드
    private String movieNm;  // 영화명(국문)
    private String openDt;  // 영화의 개봉일
    private String salesAcc;  // 누적매출액
    private String audiAcc;  // 누적관객수



    @ManyToMany
    private Set<Member> voter;
    /*DB 저장 일자*/


    @ManyToOne
    private MovieInfo movieInfo;


}
