package com.korea.Team5.weeklyMovie;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoxOfficeResult {
    private String boxofficeType;
    private String showRange;
    private String yearWeekTime;
    private List<WeeklyMovie> weeklyBoxOfficeList;
}
