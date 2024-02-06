package com.korea.Team5.Comment;


import com.korea.Team5.Comment.Commentreply.CommentReply;
import com.korea.Team5.USER.Member;
import com.korea.Team5.board.article.Article;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String content;

  @ManyToMany
  Set<Member> voter;

  private LocalDateTime createDate;

  private LocalDateTime modifyDate;//수정일시
  @ManyToOne
  private Member member;

  @ManyToOne
  private Article article;

  @OneToMany(mappedBy = "comment",cascade = CascadeType.REMOVE)
  private List<CommentReply> commentReplyList;


}