package com.korea.Team5.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Actor1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String peopleNm;  // 배우 이름
    private String cast; // 역할

    @ManyToOne
    private MovieInfo movieInfo;
}
