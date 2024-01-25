package com.korea.Team5.board;

import com.korea.Team5.USER.Member;
import com.korea.Team5.board.article.Article;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Article> articleList;

}










