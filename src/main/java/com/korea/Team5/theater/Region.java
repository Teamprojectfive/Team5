package com.korea.Team5.theater;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Region {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  //구나,시지역
  @OneToMany(mappedBy = "region")
  private List<SmallRegion> smallRegion;

  @ManyToOne
  private Excel excel;


}
