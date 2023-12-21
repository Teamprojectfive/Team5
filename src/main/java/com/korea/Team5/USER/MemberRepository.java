package com.korea.Team5.USER;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByloginId(String loginId);



    //회원가입시 아이디,닉네임,이메일,전화번호 중복예외처리 메서드
  boolean existsByLoginIdOrEmailOrNickNameOrPhone(String loginId, String email, String nickName, String phone);
}
