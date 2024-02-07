package com.korea.Team5.board.Comment.Commentreply;

import com.korea.Team5.board.Comment.Comment;
import com.korea.Team5.USER.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class CommentReply {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String content;


  @ManyToMany
  Set<Member> voter; // 추천 ,좋아요


  private LocalDateTime createDate;

  private LocalDateTime modifyDate;//수정일시

  @ManyToOne
  private Member member;

  @ManyToOne
  private Comment comment;
}
