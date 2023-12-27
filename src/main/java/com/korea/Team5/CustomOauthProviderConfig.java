package com.korea.Team5;

import com.korea.Team5.USER.MemberService;
import com.korea.Team5.USER.SocialOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;

@Configuration
public class CustomOauthProviderConfig {

  @Autowired
  private MemberService memberService;

  @Autowired
  private SocialOAuth2UserService socialOAuth2UserService;
  @Bean
  OAuth2AccessTokenResponseClient getOAuth2AccessTokenResponseClient() {
    return new DefaultClientCredentialsTokenResponseClient();
  }
}
