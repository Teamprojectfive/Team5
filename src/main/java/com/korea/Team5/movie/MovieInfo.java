package com.korea.Team5.movie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String movieCd;    // 영화 코드
    private String movieNm;    // 영화 이름
    private String movieNmEn;  // 영화 영문 이름
    private String movieNmOg;  // 영화 원문 이름
    private int showTm;        // 상영 시간
    private int prdtYear;      // 제작 연도
    private String openDt;     // 개봉 일자
    private String prdtStatNm; // 제작 상태
    private String typeNm;     // 영화 타입 (장편 등)


    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.ALL)
    private List<Nation> nations;  // 국가 정보 (리스트)

    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.ALL)
    private List<Genre> genres;  // 장르 정보 (리스트)
    //
//
    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.ALL)
    private List<Director> directors;  // 감독 정보 (리스트)
    //
//
    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.ALL)
    private List<Actor1> actors;  // 배우 정보 (리스트)
    //
//
//    private List<String> showTypes;  // 상영 유형 정보 (리스트)
//
//
    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.ALL)
    private List<Company> companys;  // 제작 및 배급 회사 정보 (리스트)
//
//

    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.ALL)
    private List<Audit> audits;  // 심의 정보 (리스트)
//
//
//    private List<Staff> staffs;  // 스태프 정보 (리스트)
//

    @OneToOne
    private Movie movie;

}
