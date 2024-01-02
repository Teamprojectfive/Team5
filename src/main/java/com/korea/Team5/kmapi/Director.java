package com.korea.Team5.kmapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String directorNm;
    private String directorEnNm;
    private String directorId;

//    @ManyToOne
//    @JoinColumn(name = "director_wrap_id")
//    private DirectorWrap directorWrap;

    @ManyToOne
    private MovieInfo movieInfo;

    @ManyToOne
    private DirectorWrap directorWrap;

}
