package com.korea.Team5.board;

import com.korea.Team5.USER.Member;
import com.korea.Team5.movie.entity.MovieInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String posterUrl;




}










