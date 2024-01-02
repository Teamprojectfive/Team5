package com.korea.Team5.testMovieList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String peopleNm;

    @ManyToOne
    private TestMovieList testMovieList;

}
