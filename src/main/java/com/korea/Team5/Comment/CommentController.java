package com.korea.Team5.Comment;

import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Comment")
public class CommentController {


  private final MemberService memberService;
  private final CommentService commentService;


  @GetMapping("/list")
  public String list(){



    return "boardListdetail";
  }
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create")
  public String create(@RequestParam String entercomment, Model model,String loginId){
    Member member = this.memberService.getMember(loginId);
    model.addAttribute("member",member);


    return "boardListdetail";
  }
}
