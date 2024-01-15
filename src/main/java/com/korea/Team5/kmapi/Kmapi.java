//package com.korea.Team5.kmapi;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.korea.Team5.movie.Audit;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Setter
//@Getter
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
//public class Kmapi {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @JsonProperty("title")
//    private String title;
//
//    @JsonProperty("plots")
//    @OneToMany(mappedBy = "kmapi" ,cascade = CascadeType.ALL)
//    @Column(columnDefinition = "LONGTEXT")
//    private List<Plot> plot;
//
//    @JsonProperty("posters")
//    @Column(columnDefinition = "LONGTEXT")
//    private String posters;
//
////    @OneToMany(mappedBy = "kmapi", cascade = CascadeType.ALL)
////    @JsonProperty("plot")
////    private List<Plot> plotList;
//
//
//
//
////    @JsonProperty("directors")
////    @OneToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "director_wrap_id")
////    private DirectorWrap directorWrap;
//
////    @OneToMany(mappedBy = "movieInfo", cascade = CascadeType.REMOVE)
////    @JsonProperty("director")
////    private List<Director> directorList;
//
//}
