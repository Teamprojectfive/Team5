package com.korea.Team5.kmapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.korea.Team5.movie.MovieInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "LONGTEXT")
    private String vodUrl;

    @ManyToOne
    private MovieInfo movieInfo;

}
