package com.korea.Team5.USER;

import com.korea.Team5.USER.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByloginId(String loginId);
    Optional<Member> findByNickName(String nickName);

    List<Member> findByEmail(String email);
    List<Member> findByPhone(String phone);
    //회원가입시 아이디,닉네임 중복예외처리 메서드
  boolean existsByLoginIdOrNickName(String loginId, String nickName);



}
