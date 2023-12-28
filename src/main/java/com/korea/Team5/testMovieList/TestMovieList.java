package com.korea.Team5.testMovieList;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Map;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class TestMovieList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totCnt;

    private String source;

    private String movieCd;  // 영화 코드

    private String movieNmKo;  // 영화명(국문)

    private String movieNmEn;  // 영화명(영문)

    private String prdtYear;  // 제작 연도

    private String openDt;  // 개봉일

    private String typeNm;  // 영화 유형

    private String prdtStatNm;  // 제작 상태

    private String nationAlt;  // 제작 국가(전체)

    private String genreAlt;  // 영화 장르(전체)

    private String repNationNm;  // 대표 제작 국가명

    private String repGenreNm;  // 대표 장르명

    private String peopleNm;  // 영화 감독명

    @OneToMany(mappedBy = "testMovieList")
    private List<Director> directors;

//    private List<String> companys;  // 제작사

    private String companyCd;  // 제작사 코드

    private String companyNm;  // 제작사명

    private String movieNm;



}
