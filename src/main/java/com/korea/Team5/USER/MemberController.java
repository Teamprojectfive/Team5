package com.korea.Team5.USER;


import com.korea.Team5.DataNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/login")
  public String login() {
    return "/LoginandSignup/login_form";
  }


  @GetMapping("/signup")
  public String signup(MemberCreateForm memberCreateForm) {
    return "/LoginandSignup/signup_form";

  }

  @GetMapping("/mypage")
  public String mypage() {

    return "/LoginandSignup/mypage_form";
  }

  @PostMapping("/signup")
  public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/LoginandSignup/signup_form";
    }
    if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect",
              "2개의 패스워드가 일치하지 않습니다.");
      return "/LoginandSignup/signup_form";
    }
    if (memberService.isDuplicated(memberCreateForm.getLoginId(), memberCreateForm.getEmail(), memberCreateForm.getNickName(), memberCreateForm.getPhone())) {
      // 중복된 값이 있을 경우 에러 처리
      bindingResult.rejectValue("loginId", "duplicateValue", "이미 사용 중인 아이디입니다.");
      bindingResult.rejectValue("email", "duplicateValue", "이미 사용 중인 이메일입니다.");
      bindingResult.rejectValue("nickName", "duplicateValue", "이미 사용 중인 닉네임입니다.");
      bindingResult.rejectValue("phone", "duplicateValue", "이미 사용 중인 휴대전화번호입니다.");
      return "/LoginandSignup/signup_form";
    }


    memberService.create(memberCreateForm.getLoginId(), memberCreateForm.getNickName(), memberCreateForm.getPassword1(), memberCreateForm.getEmail()
            , memberCreateForm.getPhone());

    return "redirect:/main";
  }

  @GetMapping("/duplicate")
  public String checkNickname(HttpSession session, Model model) {

    // 세션에서 중복된 닉네임 가져오기
    String duplicatedNickName = (String) session.getAttribute("duplicatedNickName");

    if (duplicatedNickName != null) {
      // 중복이면 재설정 폼으로 이동
      session.removeAttribute("duplicatedNickName");

      return "/LoginandSignup/socialIndex_form";
    } else {
      // 중복이 아니면 메인으로 리다이렉트
      return "redirect:/main";
    }
  }
  @PostMapping("/duplicate")
  public String checkNickname(@RequestParam("nickname") String newNickname, HttpSession session, Model model) {
    String loginId = (String) session.getAttribute("socialLoginId");
    String socialProvider = (String) session.getAttribute("Provider");
    if (loginId == null) {
      throw new DataNotFoundException("socialLoginId not found in session");
    }
    // 서비스에서 중복 여부 확인
    boolean isDuplicated = memberService.isNickNameDuplicated(newNickname);
    // 중복이면 다시 입력하라는 에러 메세지 처리 또는 다른 작업을 수행
    if (isDuplicated) {
      model.addAttribute("error", "닉네임이 중복되었습니다. 다른 닉네임을 입력해주세요.");
      // 에러 메세지를 모델에 추가하고, 이후 필요한 처리를 수행할 수 있습니다.
      return "socialIndex_form"; // 에러 메세지를 표시하는 뷰로 리턴하거나 다른 리다이렉트 또는 처리를 수행할 수 있습니다.
    }

    // 서비스에서 닉네임 업데이트 확인
    memberService.updateNickname(loginId, newNickname,socialProvider);

    // 이후 리다이렉트 또는 다른 처리를 추가할 수 있습니다.
    return "redirect:/main";
  }
}
