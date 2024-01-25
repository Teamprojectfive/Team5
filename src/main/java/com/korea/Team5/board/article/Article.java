package com.korea.Team5.board.article;
import com.korea.Team5.Comment.Comment;
import com.korea.Team5.USER.Member;
import com.korea.Team5.board.Board;
import com.korea.Team5.USER.Member;
import com.korea.Team5.board.Board;
import jakarta.persistence.*;
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
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Board board;

    @OneToMany(mappedBy = "article")
    private List<Comment> commentList;

}
