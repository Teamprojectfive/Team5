package com.korea.Team5.testMovieList;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieListResult {
    private int totCnt;
    private String source;
    private List<TestMovieList> movieList;
}
