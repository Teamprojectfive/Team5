package com.korea.Team5.USER;


import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.Email.EmailService;
import com.korea.Team5.SMS.SMSService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;
  private final SMSService smsService;
  private final EmailService emailService;

  @GetMapping("/login")
  public String login() {
    return "/LoginandSignup/login_form";
  }


  @GetMapping("/signup")
  public String signup(MemberCreateForm memberCreateForm) {
    return "/LoginandSignup/signup_form";

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
    if (memberService.isDuplicated(memberCreateForm.getLoginId(),memberCreateForm.getNickName())) {
      // 중복된 값이 있을 경우 에러 처리
      bindingResult.rejectValue("loginId", "duplicateValue", "이미 사용 중인 아이디입니다.");
      bindingResult.rejectValue("nickName", "duplicateValue", "이미 사용 중인 닉네임입니다.");
      return "/LoginandSignup/signup_form";
    }


    memberService.create(memberCreateForm.getLoginId(), memberCreateForm.getNickName(), memberCreateForm.getPassword1(), memberCreateForm.getEmail()
            , memberCreateForm.getPhone());

    return "redirect:/main";
  }

  @GetMapping("/duplicate")
  public String checkNickname(HttpSession session) {

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
    LocalDateTime createDate = (LocalDateTime) session.getAttribute("createdate");
    if (loginId == null) {
      throw new DataNotFoundException("socialLoginId not found in session");
    }
    // 서비스에서 중복 여부 확인
    boolean isDuplicated = memberService.isNickNameDuplicated(newNickname);
    // 중복이면 다시 입력하라는 에러 메세지 처리 또는 다른 작업을 수행
    if (isDuplicated) {
      model.addAttribute("error", "닉네임이 중복되었습니다. 다른 닉네임을 입력해주세요.");
      // 에러 메세지를 모델에 추가하고, 이후 필요한 처리를 수행할 수 있습니다.
      return "/LoginandSignup/socialIndex_form"; // 에러 메세지를 표시하는 뷰로 리턴하거나 다른 리다이렉트 또는 처리를 수행할 수 있습니다.
    }

    // 서비스에서 닉네임 업데이트 확인
    memberService.updateNickname(loginId, newNickname,socialProvider,createDate);

    // 이후 리다이렉트 또는 다른 처리를 추가할 수 있습니다.
    return "redirect:/main";
  }
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/mypage")
  public String mypage(Model model, Principal principal) {

    Member member = this.memberService.getMember(principal.getName());
    // 모델에 Member 객체 추가
    model.addAttribute("member", member);
    return "/LoginandSignup/mypage_form";
  }
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/mypage")
  public String mypage(Model model, Principal principal, @RequestParam("loginId")String loginId, @RequestParam("nickName") String nickName, @RequestParam("phone") String phone, @RequestParam("email") String email
                      ,@RequestParam("createDate")LocalDateTime createDate){


//    // 위에서 받아온 데이터를 이용하여 DB 업데이트 로직 수행
    Member member = memberService.updateMember(principal.getName(), nickName, phone, email,createDate);
    model.addAttribute("member", member);


    return "/LoginandSignup/mypage_form";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/updatePhone")
  public String updatePhone() {

    return "/LoginandSignup/phoneMypage"; // 적절한 리다이렉트 경로로 변경
  }
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/updatePhone")
  public String updatePhone(@RequestParam String phone, Model model, HttpSession session) {
    String verificationCode = emailService.sendVerificationCodeSMS(phone);
    // 생성된 인증 코드를 세션에 저장
    session.setAttribute("verificationCode", verificationCode);
    // 모델에 전화번호를 추가하여 폼에 전달
    model.addAttribute("phone", phone);
    return "/LoginandSignup/phoneMypage";
  }
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/updatePhoneverification")
  public String updatePhoneverifi(@RequestParam String phone, @RequestParam String verificationCode, Model model, HttpSession session,Principal principal) {
    // 세션에서 저장된 전송된 인증 코드 가져오기
    String storedVerificationCode = (String) session.getAttribute("verificationCode");
    // 입력한 인증 코드와 전송된 인증 코드 비교
    if (storedVerificationCode != null && storedVerificationCode.equals(verificationCode)) {
      model.addAttribute("phone", phone);
      // 인증 성공 시 처리 (예: DB에 전화번호 업데이트)
      memberService.updateMemberPhone(principal.getName(),phone);
      // 인증 성공 시 처리 (예: 마이페이지로 리다이렉션)
      return "redirect:/member/mypage";
    } else {
      // 인증 실패 시 에러 메시지 설정
      model.addAttribute("errorMessage", "인증번호가 일치하지 않습니다.");
      // 모델에 전화번호를 추가하여 폼에 전달
      model.addAttribute("phone", phone);
      // 이전 페이지로 이동
      return "/LoginandSignup/phoneMypage";
    }
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/mypage/email")
  public String updateEmail() {
    // 이메일 업데이트 로직 수행


    return "LoginandSignup/emailMypage"; // 적절한 리다이렉트 경로로 변경
  }
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/mypage/email")
  public String updateEmail(Model model,@RequestParam String email,@RequestParam String verificationCode ,HttpSession session){
    String storedVerificationCode = String.valueOf((int) (Math.random() * 9000) + 1000);
// 이메일 전송 로직
     emailService.sendVerificationCode(email,storedVerificationCode);

    // 생성된 인증 코드를 세션에 저장
    session.setAttribute("verificationCode", storedVerificationCode);

    // 모델에 이메일을 추가하여 폼에 전달
    model.addAttribute("email", email);

    return "/LoginandSignup/emailMypage";
  }
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/mypage/emailverification")
  public String emailverification(Model model,@RequestParam String email,HttpSession session,@RequestParam String verificationCode,Principal principal){

    // 세션에서 저장된 전송된 인증 코드 가져오기
    String storedVerificationCode = (String) session.getAttribute("verificationCode");

    // 입력한 인증 코드와 전송된 인증 코드 비교
    if (storedVerificationCode != null && storedVerificationCode.equals(verificationCode)) {
      model.addAttribute("email", email);
      // 인증 성공 시 처리 (예: DB에 이메일 업데이트)
      memberService.updateMemberemail(principal.getName(),email);
      // 인증 성공 시 처리 (예: 마이페이지로 리다이렉션)
      return "redirect:/member/mypage";
    } else {
      // 인증 실패 시 에러 메시지 설정
      model.addAttribute("errorMessage", "인증번호가 일치하지 않습니다.");
      // 모델에 이메일를 추가하여 폼에 전달
      model.addAttribute("email", email);
      // 이전 페이지로 이동
      return "/LoginandSignup/emailMypage";
    }
  }


//마이페이지 닉네임 수정 부분//
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/mypage/nickName")
  public String updatenickName(){


    return "LoginandSignup/nickNameMypage";
  }
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/mypage/nickName")
  public String updatenickName(@RequestParam("nickName") String nickName,Model model,Principal principal) {

    Member member1 = memberService.getMember(principal.getName());

    // 새로운 닉네임이 현재의 닉네임과 같은지 확인
    if (nickName.equals(member1.getNickName())) {
      // 닉네임이 같으면 에러 메시지를 사용자에게 보여줌
      model.addAttribute("errorMessage", "현재 사용 중인 닉네임과 동일합니다.");
      return "LoginandSignup/nickNameMypage";
    }
    // 새로운 닉네임이 이미 다른 사용자에 의해 사용 중인지 확인
    if (memberService.isNickNameDuplicated(nickName)) {
      // 닉네임이 이미 사용 중이면 에러 메시지를 사용자에게 보여줌
      model.addAttribute("errorMessage", "이미 사용 중인 닉네임입니다.");
      return "LoginandSignup/nickNameMypage";
    }
    // 새로운 닉네임이 입력되지 않았을 경우, 현재의 닉네임을 그대로 사용
    String newNickName = (nickName == null || nickName.trim().isEmpty()) ? member1.getNickName() : nickName;
    // 닉네임이 변경되었는지 확인
    if (!newNickName.equals(member1.getNickName())) {
      // 닉네임이 변경되었을 때만 업데이트 진행
      member1.setNickName(newNickName);
      memberService.updateMember(member1.getLoginId(), newNickName, member1.getPhone(), member1.getEmail(), member1.getCreateDate());
    }
    return "redirect:/member/mypage"; // 적절한 페이지로 리다이렉트
  }

  @GetMapping("/findId")
  public String findId(){


    return "/LoginandSignup/findId_form";
  }
  @PostMapping("/findId")
  public String findId(Model model,@RequestParam String verificationcode,HttpSession session,@RequestParam String email){

    String storedVerificationCode = String.valueOf((int) (Math.random() * 9000) + 1000);
// 이메일 전송 로직
    emailService.sendVerificationCode(email,storedVerificationCode);

    // 생성된 인증 코드를 세션에 저장
    session.setAttribute("verificationCode", storedVerificationCode);

    // 모델에 이메일을 추가하여 폼에 전달
    model.addAttribute("email", email);

    return "/LoginandSignup/findId_form";

  }

}
