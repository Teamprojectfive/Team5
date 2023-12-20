package com.korea.Team5;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Api {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*박스오피스 종류*/
    private String boxofficeType;

    /*박스오피스 조회 일자*/
    private String showRange;

    /*순번*/
    private String rnum;
}
