package com.korea.Team5.kmapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class PlotWrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("plot")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plotWrap")
    private List<Plot> plots;


//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plotWrap")
//    private List<Plot> plots;

}
