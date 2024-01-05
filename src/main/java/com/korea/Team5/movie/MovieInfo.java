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









}
