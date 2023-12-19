package com.korea.Team5.USER;

import com.korea.Team5.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public Member create(String loginId, String nickName, String password, String email, String phone){
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


}
