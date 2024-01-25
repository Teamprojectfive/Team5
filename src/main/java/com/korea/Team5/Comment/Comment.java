package com.korea.Team5.Comment;


import com.korea.Team5.USER.Member;
import com.korea.Team5.board.article.Article;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String content;

  private String voter;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;//수정일시
  @ManyToOne
  private Member member;

  @ManyToOne
  private Article article;
}
