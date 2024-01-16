package com.korea.Team5.movie;

import com.korea.Team5.Review.Review;
import com.korea.Team5.USER.Member;
import com.nimbusds.oauth2.sdk.id.Actor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content; // 내용


    private String image; // 무비포스터

    private String cast; // 출연진

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    private LocalDateTime modifyDate;

    private String boxofficeType;  // 박스오피스 종류
    private String showRange;  // 대상 상영기간
    private String yearWeekTime;  // 조회일자에 해당하는 연도와 주차
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
    private String targetDt;

    @Column(name = "modification_date_time")
    @CreationTimestamp
    private LocalDateTime modificationDateTime;



    @ManyToOne
    private MovieInfo movieInfo;









}
