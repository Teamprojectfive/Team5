package com.korea.Team5;

import com.korea.Team5.USER.SocialOAuth2UserService;
import com.korea.Team5.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Autowired
  private SocialOAuth2UserService socialOAuth2UserService;

  private MovieService movieService;


  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorizeHttpRequests ->
                    authorizeHttpRequests
                            .requestMatchers("/review/create/**").authenticated()
                            .requestMatchers("/board/article/modify/**").authenticated()
                            .requestMatchers("/board/create/**").authenticated()
                            .requestMatchers("/Comment/vote/**").authenticated()
                            .requestMatchers("/board/create/**").authenticated()
                            .requestMatchers("/board/article/create/**").authenticated()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/notice/create").hasRole("ADMIN")
                            .requestMatchers("/**").permitAll()
                            .anyRequest().authenticated()
            )
            .csrf((csrf) -> csrf
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            .headers((headers) -> headers
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                            XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
            .formLogin((formLogin) -> formLogin
                    .loginPage("/member/login")
                    .defaultSuccessUrl("/main"))
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/main")
                    .invalidateHttpSession(true))
            // 서큐리티도 중요함!. 아웃로그인방식을쓰기에 이렇게해야함! 고정이라고생각하면됨.
            .oauth2Login((oauth2Login) -> oauth2Login //OAuth2 로그인을 설정하는 메서드
                    .loginPage("/member/login")
                    .defaultSuccessUrl("/member/duplicate")
                    .userInfoEndpoint(userInfoEndpoint ->//OAuth2 로그인이 성공한 후에 호출되는 사용자 정보 엔드포인트를 설정하는 메서드입니다.
                            userInfoEndpoint//ocialOAuth2UserService를 사용하여 사용자 정보를 처리하도록 설정합니다.
                                    .userService(socialOAuth2UserService))
                    .failureHandler((request, response, exception) -> {
                      response.sendRedirect("/member/duplicate");
                    })
            );

    ;
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();


  }









}