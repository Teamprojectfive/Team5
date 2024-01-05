package com.korea.Team5.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;


@Getter
@Setter
@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String peopleNm;  // 감독 이름
    private String peopleNmEn;  // 감독 영어 이름

    @ManyToOne
    private MovieInfo movieInfo;
}
