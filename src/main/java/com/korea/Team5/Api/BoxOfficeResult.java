package com.korea.Team5.Api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoxOfficeResult {
    private String boxofficeType;
    private String showRange;
    private List<DailyMovie> dailyBoxOfficeList;
}
