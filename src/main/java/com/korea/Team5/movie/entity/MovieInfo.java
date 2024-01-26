package com.korea.Team5.movie.entity;




import com.korea.Team5.USER.Member;


import com.korea.Team5.board.Board;
import com.korea.Team5.board.article.Article;
import com.korea.Team5.kmapi.entity.Plot;

import com.korea.Team5.kmapi.entity.Vod;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String movieCd;    // 영화 코드
    private String movieNm;    // 영화 이름
    private int showTm;        // 상영 시간
    private String prdtYear;      // 제작 연도
    private String openDt;     // 개봉 일자

    @Column(columnDefinition = "LONGTEXT")
    private String posters;

    @OneToMany(mappedBy = "movieInfo")
    private List<Nation> nations;  // 국가 정보 (리스트)

    @OneToMany(mappedBy = "movieInfo")
    private List<GenreMovieInfo> genreMovieInfos = new ArrayList<>();  // 장르 정보 (리스트)

    @Transient
    private List<GenreDto> genres = new ArrayList<>();

    @OneToMany(mappedBy = "movieInfo")
    private List<Director> directors;  // 감독 정보 (리스트)

    @OneToMany(mappedBy = "movieInfo")
    private List<Actor1> actors;  // 배우 정보 (리스트)

    @OneToMany(mappedBy = "movieInfo")
    private List<Vod> vods;


    @OneToMany(mappedBy = "movieInfo")
    private List<Staff> staffs;

    @ManyToMany
    private List<Member> voter;



    @OneToMany(mappedBy = "movieInfo")
    private List<Company> companys;  // 제작 및 배급 회사 정보 (리스트)


    @OneToMany(mappedBy = "movieInfo")
    private List<Audit> audits;  // 심의 정보 (리스트)

    @OneToMany(mappedBy = "movieInfo")
    @Column(columnDefinition = "LONGTEXT")
    private List<Plot> plot;

    @OneToMany(mappedBy = "movieInfo")
    private List<Movie> movieList;

    @OneToMany(mappedBy = "movieInfo")
    private List<Article> articleList;













}
