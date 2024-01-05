package com.korea.Team5.movie;

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
    private String peopleNmEn;  // 배우 영어 이름

    @ManyToOne
    private MovieInfo movieInfo;
}
