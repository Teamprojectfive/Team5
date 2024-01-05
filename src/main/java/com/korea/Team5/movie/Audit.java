package com.korea.Team5.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 엔터티의 고유 식별자

    private String auditNo;  // 심의 번호
    private String watchGradeNm;  // 관람 등급

    @ManyToOne
    private MovieInfo movieInfo;
}
