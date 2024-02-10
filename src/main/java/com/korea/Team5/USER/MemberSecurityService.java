package com.korea.Team5.USER;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    Optional<Member> _User = this.memberRepository.findByloginId(loginId);
    if (_User.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
    }
    Member member = _User.get();
    List<GrantedAuthority> authorities = new ArrayList<>();
    if ("admin".equals(loginId)) {
      authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
    } else {
      authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
    }
    return new User(member.getLoginId(), member.getPassword(),authorities);
  }


}
