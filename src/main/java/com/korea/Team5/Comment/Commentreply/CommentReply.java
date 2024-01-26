package com.korea.Team5.Comment.Commentreply;

import com.korea.Team5.Comment.Comment;
import com.korea.Team5.USER.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CommentReply {
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
  private Comment comment;
}
