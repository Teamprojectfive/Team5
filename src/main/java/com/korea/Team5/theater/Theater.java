package com.korea.Team5.theater;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Theater {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;
  // 상영관 명
  private String name;
  //광역시지역
  @OneToMany(mappedBy = "theater")
  private List<Region> region;
  //영화 상영관코드
  private String code;
  //영화 상영사업주회사이름
  private String businessname;
  //운영상태
  private String operatingstatus;
  //스크린수
  private String screensnum;
  //좌석수
  private String seatsnum;
  //주소
  private String address;

}
