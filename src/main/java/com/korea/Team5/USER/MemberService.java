package com.korea.Team5.USER;

import com.korea.Team5.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
  @Autowired
  private final MemberRepository memberRepository;


  public Member create(String loginId, String nickName, String password, String email, String phone) {
    Member member = new Member();

    member.setLoginId(loginId);
    member.setNickName(nickName);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    member.setPassword(passwordEncoder.encode(password));
    member.setEmail(email);
    member.setPhone(phone);

    member.setCreateDate(LocalDateTime.now());


    this.memberRepository.save(member);

    return member;

  }

  public boolean isDuplicated(String loginId, String nickName) {
    return memberRepository.existsByLoginIdOrNickName(loginId, nickName);
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

  public Member saveOrUpdateSocialMember(String loginId, String socialProvider, String nickName, String name, LocalDateTime localDateTime) {


    // 소셜 로그인 사용자 정보를 가져오거나 생성합니다.
    Member member = memberRepository.findByloginId(loginId)
            .orElse(new Member());

    // 공통 속성 설정
    member.setLoginId(loginId);
    member.setNickName(nickName);
    member.setSocialProvider(socialProvider);

    member.setName(name);
    member.setCreateDate(LocalDateTime.now());
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


  // 소셜로그인 닉네임 업데이트 메서드 추가
  public void updateNickname(String loginId, String newNickname, String socialProvider, LocalDateTime localDateTime) {

    if (loginId == null) {
      throw new DataNotFoundException("socialLoginId not found in session");
    }
    // 세션에서 가져온 아이디로 데이터 조회
    Member member = new Member();
    member.setLoginId(loginId);
    member.setNickName(newNickname);
    member.setSocialProvider(socialProvider);
    member.setCreateDate(LocalDateTime.now());
    memberRepository.save(member);
  }


  @Transactional
  public Member updateMember(String loginId, String nickName, String phone, String email, LocalDateTime createDate) {
    // 트랜잭션 내에서 일어나는 작업
    Optional<Member> existingMember = memberRepository.findByloginId(loginId);

    Member member = null;
    if (existingMember.isPresent()) {
      member = existingMember.get();
      member.setNickName(nickName);
      member.setPhone(phone);
      member.setEmail(email);
      member.setCreateDate(createDate);
      memberRepository.save(member);
    }
    return member;
    // 다른 작업들...
  }

  //마이페이지 휴대전화 수정로직
  public Member updateMemberPhone(String loginId, String phone) {
    // 트랜잭션 내에서 일어나는 작업
    Optional<Member> existingMember = memberRepository.findByloginId(loginId);

    Member member = null;
    if (existingMember.isPresent()) {
      member = existingMember.get();
      member.setPhone(phone);
      memberRepository.save(member);
    }
    return member;
    // 다른 작업들...
  }

  public Member updateMemberemail(String loginId, String email) {
    // 트랜잭션 내에서 일어나는 작업
    Optional<Member> existingMember = memberRepository.findByloginId(loginId);

    Member member = null;
    if (existingMember.isPresent()) {
      member = existingMember.get();
      member.setEmail(email);
      memberRepository.save(member);
    }
    return member;
  }
  public List<Member> getMembersByEmail(String email) {
    List<Member> members = this.memberRepository.findByEmail(email);
    if (!members.isEmpty()) {
      return members;
    } else {
      throw new DataNotFoundException("members not found for email: " + email);
    }
  }
  public List<Member> getMembersByPhone(String phone){
    List<Member> members = this.memberRepository.findByPhone(phone);

    if (!members.isEmpty()){
      return members;
    }else {
      throw new DataNotFoundException("members not found for phone: " + phone);
    }
  }
}

