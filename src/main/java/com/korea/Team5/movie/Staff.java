package com.korea.Team5.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String peopleNm;  // 스태프 이름


    @ManyToOne
    private MovieInfo movieInfo;
}
