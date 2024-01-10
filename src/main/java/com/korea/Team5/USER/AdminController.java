package com.korea.Team5.USER;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.Review.Review;
import com.korea.Team5.Review.ReviewService;
import com.korea.Team5.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final MemberService memberService;
  private final ReviewService reviewService;
  private final MovieService movieService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/adminpage")
  public String adminpage() {


    return "admin_form";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/adminsignup")
  public String adminsignup(Model model, @RequestParam String loginId) {
    try {
      // DB에서 해당 loginId에 대한 Member를 찾아옵니다.
      Member memberFromDB = memberService.getMember(loginId);
      // DB에 해당 loginId가 존재할 경우의 로직
      // DB에 해당 loginId가 존재할 경우의 로직
      model.addAttribute("validSearch", true);
      model.addAttribute("memberExists", true);
      model.addAttribute("memberFromDB", memberFromDB);
      return "admin_form"; // 일치하는 경우
    } catch (DataNotFoundException ex) {
      // DB에 해당 loginId가 존재하지 않을 경우의 로직
      model.addAttribute("errorMessage", "해당 아이디가 존재하지 않습니다.");
      model.addAttribute("validSearch", true);
      model.addAttribute("memberExists", false);
      return "admin_form";
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/admindelete")
  public String admindelete(@RequestParam String loginId, Model model) {
    try {
      // 서비스를 통해 해당 회원 삭제
      memberService.deleteMember(loginId);
      // 삭제가 성공했을 경우, 다시 검색 폼으로 이동
      return "redirect:/admin/adminpage";
    } catch (DataNotFoundException e) {
      // 데이터를 찾을 수 없는 경우의 예외 처리
      model.addAttribute("errorMessage", "해당 아이디가 존재하지 않습니다.");
      return "admin_form"; // 예를 들어, notfound 뷰로 이동하거나 다른 방식으로 처리 가능
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/adminreview")
  public String adminreview(@RequestParam String loginId, Model model,@RequestParam(value="page", defaultValue="0") int page) {
    model.addAttribute("loginId", loginId);
    try {
      Member member = memberService.getMember(loginId);

      // 로그인한 멤버가 작성한 리뷰 목록을 가져옴
      Page<Review> paging = this.reviewService.getListReveiwMember(page, member);
      // 가져온 리뷰 목록이 비어 있는지 확인
      if (paging.isEmpty()) {
        model.addAttribute("error", "작성한 리뷰가 존재하지 않습니다.");
        return "admin_form";
      }
      // 가져온 리뷰 목록을 모델에 추가
      model.addAttribute("paging",paging);
      return "admin_form";
    } catch (DataNotFoundException e) {
      // 데이터를 찾을 수 없는 경우의 예외 처리
      model.addAttribute("error", "작성한 리뷰가 존재하지 않습니다.");
      return "admin_form";
    }

  }
}
