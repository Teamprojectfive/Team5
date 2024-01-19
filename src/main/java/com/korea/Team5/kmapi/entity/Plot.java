package com.korea.Team5.kmapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.korea.Team5.movie.entity.MovieInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "LONGTEXT")
    private String plotText;


    @ManyToOne
    private MovieInfo movieInfo;









}
