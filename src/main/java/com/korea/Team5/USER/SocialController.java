package com.korea.Team5.USER;

import
        lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
public class SocialController {

    private final MemberService memberService;
  @PostMapping("/google")
  public ResponseEntity<String> googleLogin(@RequestBody Member request) {
    Member member = memberService.saveOrUpdateSocialMember(request.getLoginId(),request.getNickName(),request.getSocialProvider(),request.getName(),request.getCreateDate());
    // 처리 결과에 따라 적절한 ResponseEntity를 반환
    return ResponseEntity.ok("Google login successful for member with ID: " + member.getId());
  }

  @PostMapping("/kakao")
  public ResponseEntity<String> kakaoLogin(@RequestBody Member request) {
    Member member = memberService.saveOrUpdateSocialMember(request.getLoginId(),request.getNickName(),request.getSocialProvider(),request.getName(),request.getCreateDate());
    // 처리 결과에 따라 적절한 ResponseEntity를 반환
    return ResponseEntity.ok("Kakao login successful for member with ID: " + member.getId());
  }

  @PostMapping("/naver")
  public ResponseEntity<String> naverLogin(@RequestBody Member request) {
    Member member = memberService.saveOrUpdateSocialMember(request.getLoginId(),request.getNickName(),request.getSocialProvider(),request.getName(),request.getCreateDate());
    // 처리 결과에 따라 적절한 ResponseEntity를 반환
    return ResponseEntity.ok("Naver login successful for member with ID: " + member.getId());
  }
}
