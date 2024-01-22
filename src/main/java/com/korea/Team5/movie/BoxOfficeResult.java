package com.korea.Team5.movie;

import com.korea.Team5.movie.entity.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoxOfficeResult {
    private String boxofficeType;
    private String showRange;
    private String yearWeekTime;
    private List<Movie> weeklyBoxOfficeList;
}
