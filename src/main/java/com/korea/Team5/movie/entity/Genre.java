package com.korea.Team5.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String genreNm;

    @OneToMany(mappedBy = "genre")
    private List<GenreMovieInfo> genreMovieInfos = new ArrayList<>();

}
