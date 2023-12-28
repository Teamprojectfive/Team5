package com.korea.Team5.api;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // API 서비스 인증키
    private String serviceKey;

    // 통합검색 출력 결과수 (최소값: 3, 최대값: 500)
    @Size(min = 3, max = 500)
    private int listCount;

    // 검색 결과 시작 번호 (0 이상의 10단위 숫자)
    private int startCount;

    // 검색 대상 컬렉션 지정
    private String collection;

    // 통합검색 질의어
    private String query;

    // 상세정보 출력 여부 (Y or N)
    private String detail;

    // 결과 정렬 (기본 정렬 값은 정확도)
    // 정확도순 정렬, title 영화명 정렬, director 감독명 정렬, company 제작사명 정렬, prodYear 제작년도 정렬
    private String sort;

    // 기간 검색 - 제작년도 시작 (YYYY 형식 문자열)
    private String createDts;

    // 기간 검색 - 제작년도 종료 (YYYY 형식 문자열)
    private String createDte;

    // 기간 검색 - 개봉일 시작 (YYYYMMDD 형식 문자열)
    private String releaseDts;

    // 기간 검색 - 개봉일 종료 (YYYYMMDD 형식 문자열)
    private String releaseDte;

    // 상세검색 - 국가명 ex) 대한민국, 일본, 오스트리아 등
    private String nation;

    // 상세검색 - 제작사명
    private String company;

    // 상세검색 - 장르명
    private String genre;

    // 상세검색 - 심의여부(y/n)
    private String ratedYn;

    // 상세검색 - 용도구분
    private String use;

    // 상세검색 - movie_Id
    private String movieId;

    // 상세검색 - movie_seq
    private String movieSeq;

    // 상세검색 - 유형명 (극영화/애니메이션/다큐멘터리/광고홍보물/교육물/뮤직비디오/실황공연물/정보물/기록물/실험영화/온라인영화/미디어아트/기타)
    private String type;

    // 상세검색 - 영화명
    private String title;

    // 상세검색 - 감독명
    private String director;

    // 상세검색 - 배우명
    private String actor;

    // 상세검색 - 스탭명
    private String staff;

    // 상세검색 - 키워드
    private String keyword;

    // 상세검색 - 줄거리
    private String plot;

}
