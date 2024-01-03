package com.korea.Team5.movie;

import com.korea.Team5.Review.Review;
import com.korea.Team5.USER.Member;
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
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 이름

    private String content; // 내용

    private String genre; // 장르

    private String age; // 등급

    private String movieRelease; // 무비개봉일

    private String image; // 무비포스터

    private String cast; // 출연진

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    private LocalDateTime modifyDate;



    @ManyToMany
    private Set<Member> voter;

    private String boxofficeType;    // 박스오피스 종류를 출력합니다.
    private String showRange;        // 대상 상영기간을 출력합니다.
    private String yearWeekTime;     // 조회일자에 해당하는 연도와 주차를 출력합니다. (YYYYIW)
    private String rnum;             // 순번을 출력합니다.
    private String rank;             // 해당일자의 박스오피스 순위를 출력합니다.
    private String rankInten;        // 전일대비 순위의 증감분을 출력합니다.
    private String rankOldAndNew;    // 랭킹에 신규진입여부를 출력합니다. “OLD” : 기존, “NEW” : 신규
    private String movieCd;          // 영화의 대표코드를 출력합니다.
    private String movieNm;          // 영화명(국문)을 출력합니다.
    private String openDt;           // 영화의 개봉일을 출력합니다.
    private String salesAmt;         // 해당일의 매출액을 출력합니다.
    private String salesShare;       // 해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
    private String salesInten;       // 전일 대비 매출액 증감분을 출력합니다.
    private String salesChange;      // 전일 대비 매출액 증감 비율을 출력합니다.
    private String salesAcc;         // 누적매출액을 출력합니다.
    private String audiCnt;          // 해당일의 관객수를 출력합니다.
    private String audiInten;        // 전일 대비 관객수 증감분을 출력합니다.
    private String audiChange;       // 전일 대비 관객수 증감 비율을 출력합니다.
    private String audiAcc;          // 누적관객수를 출력합니다.
    private String scrnCnt;          // 해당일자에 상영한 스크린수를 출력합니다.
    private String showCnt;          // 해당일자에 상영된 횟수를 출력합니다.

    /*DB 저장 일자*/
    private String targetDt;

    @Column(name = "modification_date_time")
    @CreationTimestamp
    private LocalDateTime modificationDateTime;




}
