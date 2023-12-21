package com.korea.Team5.USER;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

      private final MemberService memberService;

      @GetMapping("/login")
      public String login(){
        return "/LoginandSignup/login_form";
      }


      @GetMapping("/signup")
      public String signup(MemberCreateForm memberCreateForm){
        return "/LoginandSignup/signup_form";

      }

      @PostMapping("/signup")
      public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
            if (bindingResult.hasErrors()){
                  return "/LoginandSignup/signup_form";
            }
            if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())){
                  bindingResult.rejectValue("password2","passwordInCorrect",
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

      @PostMapping("/google")
      public ResponseEntity<String> googleLogin(@RequestBody Member request) {
            Member member = memberService.saveOrUpdateSocialMember(request.getLoginId(), request.getNickName(), "Google");
            // 처리 결과에 따라 적절한 ResponseEntity를 반환
            return ResponseEntity.ok("Google login successful for member with ID: " + member.getId());
      }

      @PostMapping("/kakao")
      public ResponseEntity<String> kakaoLogin(@RequestBody Member request) {
            Member member = memberService.saveOrUpdateSocialMember(request.getLoginId(), request.getNickName(), "Kakao");
            // 처리 결과에 따라 적절한 ResponseEntity를 반환
            return ResponseEntity.ok("Kakao login successful for member with ID: " + member.getId());
      }

      @PostMapping("/naver")
      public ResponseEntity<String> naverLogin(@RequestBody Member request) {
            Member member = memberService.saveOrUpdateSocialMember(request.getLoginId(), request.getNickName(),"Naver");
            // 처리 결과에 따라 적절한 ResponseEntity를 반환
            return ResponseEntity.ok("Naver login successful for member with ID: " + member.getId());
      }
}
