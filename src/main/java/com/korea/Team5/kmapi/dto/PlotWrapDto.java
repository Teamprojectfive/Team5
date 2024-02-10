package com.korea.Team5.kmapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korea.Team5.kmapi.entity.Plot;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlotWrapDto {

    @JsonProperty("plot")
    private List<Plot> plot;


}
