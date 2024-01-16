package com.korea.Team5.theater;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Excel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    //큰지역 광역시
    private String bigRegion;
    //시나 구,동
    private String smallRegion;

    private String code;

    private String name;

    private String screennum;

    private String setnum;

    private String twoDnum;

    private String threeDnum;

    private String businessname;

    private String  address;

    private String homepage;


    @OneToMany(mappedBy = "excel")
    private List<Region> regionList;










}
