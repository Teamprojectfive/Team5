package com.korea.Team5.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieInfoResult {
    @JsonProperty("movieInfoResult")
    private MovieInfoWrap movieInfoResult;
}
