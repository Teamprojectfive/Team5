package com.korea.Team5.USER;

import com.korea.Team5.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;


  public Member create(String loginId, String nickName, String password, String email, String phone) {
    Member member = new Member();

    member.setLoginId(loginId);
    member.setNickName(nickName);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    member.setPassword(passwordEncoder.encode(password));
    member.setEmail(email);
    member.setPhone(phone);

    this.memberRepository.save(member);

    return member;

  }

  public boolean isDuplicated(String loginId, String email, String nickName, String phone) {
    return memberRepository.existsByLoginIdOrEmailOrNickNameOrPhone(loginId, email, nickName, phone);
  }


  public Member getMember(String loginId) {
    Optional<Member> member = this.memberRepository.findByloginId(loginId);
    if (member.isPresent()) {
      return member.get();
    } else {
      throw new DataNotFoundException("member not found");
    }
  }

  // 통합 메서드로 소셜 로그인 정보 저장 또는 업데이트
  // 소셜 로그인 정보 저장 또는 업데이트
  public Member saveOrUpdateSocialMember(String loginId, String socialProvider, String nickName, String name) {
    // 소셜 로그인 사용자 정보를 가져오거나 생성합니다.
    Member member = memberRepository.findByloginId(loginId)
            .orElse(new Member());

    // 공통 속성 설정
    member.setLoginId(loginId);
    member.setNickName(nickName);
    member.setSocialProvider(socialProvider);
    ;
    member.setName(name);
    // 소셜 로그인 사용자 정보를 저장 또는 업데이트합니다.
    return memberRepository.save(member);


  }

  public boolean isNickNameDuplicated(String nickName) {
    Optional<Member> existingMember = memberRepository.findByNickName(nickName);
    return existingMember.isPresent();
  }

  public boolean isSocialMemberExists(String loginId) {
    Optional<Member> existingMember = memberRepository.findByloginId(loginId);
    return existingMember.isPresent();
  }
}
