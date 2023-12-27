package com.korea.Team5.USER;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2LoginAuthenticationProvider extends OAuth2LoginAuthenticationProvider {

  public CustomOAuth2LoginAuthenticationProvider(OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient, OAuth2UserService<OAuth2UserRequest, OAuth2User> userService) {
    super(accessTokenResponseClient, userService);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
      if (shouldBlockSocialLogin(authentication)) {
        throw new OAuth2AuthenticationException("Social login is blocked.");
      }

      return super.authenticate(authentication);
    } catch (AuthenticationException ex) {
      throw ex;
    }
  }

  private boolean shouldBlockSocialLogin(Authentication authentication) {

    return true; // 예시로 항상 막는다고 가정
  }
}
