package com.korea.Team5.Notice;


import com.korea.Team5.USER.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;

  private String subject;

  private String content;

  private LocalDateTime createDate;

  private Integer views;


  @ManyToOne
  private Member member;

  private LocalDateTime modifyDate;//수정일시

}
