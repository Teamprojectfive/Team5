package com.korea.Team5.Notice;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeCreateform {


  @NotEmpty(message = "제목은 필수항목입니다.")
  private String subject;
  @NotEmpty(message = "내용은 필수항목입니다.")
  private String content;

}
