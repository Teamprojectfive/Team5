package com.korea.Team5.kmapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInfoDto {

    @JsonProperty("title")
    private String title;
    @JsonProperty("plots")
    private PlotWrapDto plots;
    @JsonProperty("posters")
    private String posters;
    @JsonProperty("prodYear")
    private String prodYear;









//    @JsonProperty("directors")
//    private Director director;


}
