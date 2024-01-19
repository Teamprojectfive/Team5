package com.korea.Team5.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyCd;  // 회사 코드
    private String companyNm;  // 회사 이름
    private String companyNmEn;  // 회사 영어 이름
    private String companyPartNm;  // 회사 부서 이름

    @ManyToOne
    private MovieInfo movieInfo;
}
