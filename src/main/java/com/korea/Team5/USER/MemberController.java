package com.korea.Team5.USER;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

      private final MemberService memberService;

      @GetMapping("/login")
      public String login(){
        return "login_form";
      }


      @GetMapping("/signup")
      public String signup(MemberCreateForm memberCreateForm){
        return "signup_form";
      }

      @PostMapping("/signup")
      public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
            if (bindingResult.hasErrors()){
                  return "signup_form";
            }
            if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())){
                  bindingResult.rejectValue("password2","passwordInCorrect",
                          "2개의 패스워드가 일치하지 않습니다.");
                  return "signup_form";
            }
            if (memberService.isDuplicated(memberCreateForm.getLoginId(), memberCreateForm.getEmail(), memberCreateForm.getNickName(), memberCreateForm.getPhone())) {
                  // 중복된 값이 있을 경우 에러 처리
                  bindingResult.rejectValue("loginId", "duplicateValue", "이미 사용 중인 아이디입니다.");
                  bindingResult.rejectValue("email", "duplicateValue", "이미 사용 중인 이메일입니다.");
                  bindingResult.rejectValue("nickName", "duplicateValue", "이미 사용 중인 닉네임입니다.");
                  bindingResult.rejectValue("phone", "duplicateValue", "이미 사용 중인 휴대전화번호입니다.");
                  return "signup_form";
            }


            memberService.create(memberCreateForm.getLoginId(), memberCreateForm.getNickName(), memberCreateForm.getPassword1(), memberCreateForm.getEmail()
                              , memberCreateForm.getPhone());

            return "redirect:/main";
      }
}
