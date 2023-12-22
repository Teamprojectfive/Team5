package com.korea.Team5.Dummy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class dummyMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String content;

    private String genre; // 장르

    private String age; // 등급

    private String movieRelease; // 무비개봉일

    private String image; // 무비포스터

    private String cast; // 출연진
}
