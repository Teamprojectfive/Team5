package com.korea.Team5.TestApi;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ApiMovie {
    private String curPage;
    private String itemPerPage;
    private String movieNm;
    private String directNm;
    private String openStartDt;
}
