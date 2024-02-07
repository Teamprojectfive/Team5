package com.korea.Team5.board.article;


import com.korea.Team5.USER.Member;


import com.korea.Team5.movie.entity.MovieInfo;

import com.korea.Team5.board.Comment.Comment;




import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    @Size( max = 1000)
    private String content;

    private Integer views;
    private LocalDateTime createDate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private MovieInfo movieInfo;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;



}
