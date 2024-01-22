package com.korea.Team5.Notice;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

  private final MemberService memberService;
  private final NoticeService noticeService;

  @GetMapping("/list")
  public String list(NoticeCreateform noticeCreateform,Model model) {
    try {
      List<Notice> noticeList = this.noticeService.getnoticeList();
      model.addAttribute("noticeList", noticeList);
      return "/Notice/notice_form";
    } catch (DataNotFoundException e) {
      // DataNotFoundException이 발생한 경우
      // 클라이언트에게 메시지를 전달하거나, 다른 처리를 수행할 수 있습니다.
      model.addAttribute("errorMessage", "공지사항이 없습니다."); // 적절한 메시지로 수정
      return "/Notice/notice_form";
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/create")
  public String create(Model model, NoticeCreateform noticeCreateform, Member member) {

    model.addAttribute("adminLoginId", member.getLoginId());


    return "/Notice/notice_create";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create")
  public String create(Model model,@Valid NoticeCreateform noticeCreateform,
                       Principal principal) {

    Member member = this.memberService.getMember(principal.getName());
    this.noticeService.create(noticeCreateform.getSubject(), noticeCreateform.getContent(),
            member);
    model.addAttribute("member", member);

    return "redirect:/notice/list";
  }
}

