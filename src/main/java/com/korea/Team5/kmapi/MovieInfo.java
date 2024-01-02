package com.korea.Team5.kmapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    private String title;

//    @JsonProperty("directors")
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "director_wrap_id")
//    private DirectorWrap directorWrap;

    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.REMOVE)
    @JsonProperty("director")
    private List<Director> directorList;

}
