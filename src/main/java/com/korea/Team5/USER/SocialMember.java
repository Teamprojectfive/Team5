package com.korea.Team5.USER;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class SocialMember extends User implements OAuth2User {
  private Map attributes;

  public SocialMember(String username, String password, Collection<? extends GrantedAuthority> authorities, Map attributes) {
    super(username, password, authorities);
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return this.getUsername();
  }

  @Override
  public <A> A getAttribute(String name) {
    return OAuth2User.super.getAttribute(name);
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }
}
