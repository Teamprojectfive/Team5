package com.korea.Team5.USER;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Map;

@Service
public class SocialOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberService memberService;


  @Autowired
  public SocialOAuth2UserService(MemberService memberService) {
    this.memberService = memberService;
    //위에서말씀드린 그대로구요.
  }


  // 이메서드가 제일필요하고중요한부분중하나입니다. 사용자정보 끌어와서 DB에담아야하기ㄸ때문에 꼭중요하고 매서드명은
  // 고정적으로 이렇게 가져가야합니다. 크게 수정하지않고 이대로사용해서 바로사용가능할것입니다.
  // 굳이 소셜로그인 엔티티를 수정하지않는이상 기능구현에있어서 큰 오류는없을겁니다.
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    System.out.println("asdasd");// 제가 이 메서드가 실행되는지 출력문을통해서 확인한거기에 이출력문은 생략하셔도됩니다//
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();


    String clientId = userRequest.getClientRegistration().getClientId();
    String clientName = userRequest.getClientRegistration().getClientName();

    OAuth2User oAuth2User = delegate.loadUser(userRequest);


    if (clientName.equals("kakao")) {
      System.out.println("kakao");
    } else if (clientName.equals("Google")) {
      System.out.println("google");
    }


    // 여기서부턴 IF문과 ELSE IF문을 활용해서 제가 코드를짜서 구글,카카오,네이버로그인시 사용자 정보를 끌어서
    // 가져오기위해서  각엔티티별로 이렇게 정보를추출해서 DB에담아지게 하였습니다 .각 소셜사이트마다 엔티티도다르고,
    // API도 상이한부분이있기때문에 조금차이가 있을것입니다 네이버와 카카오같은경우는 Map<String, Object> attributes
    //각 소셜 로그인 서비스(provider)는 사용자 정보를 다양한 속성으로 제공합니다.
    // Kakao는 id, kakao_account.email, nickname 등을 제공하고, Naver는 id, response.email, response.nickname, response.name 등 서로상이함.
    // 이러한 다양한 속성을 효과적으로 다루기 위해 맵을 사용했고 구글은 속성자체가 꽤나단순해서 이렇게해줬습니다.
    System.out.println("User Authorities: " + oAuth2User.getAuthorities());

    String loginId = null;

    if (clientName.equals("Google")) {
      // Google 로그인 사용자 정보 추출

      loginId = oAuth2User.getAttribute("sub");
      String socialProvider = userRequest.getClientRegistration().getClientName();
      String nickName = oAuth2User.getAttribute("given_name");
      String name = oAuth2User.getAttribute("name");
      if (memberService.isSocialMemberExists(loginId)) {
        // 이미 등록된 소셜 사용자인 경우에 대한 처리를 여기에 추가
        // 예시로 로그에 출력하는 코드를 추가했습니다.
        System.out.println("Social User Already Exists: " + loginId);
        // 중복 처리 로직을 추가하거나 세션에 정보를 추가
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("existingSocialUser", loginId);
      }
      if (!memberService.isSocialMemberExists(loginId) && !memberService.isNickNameDuplicated(nickName)) {
        // Save or update Naver information in the database
        memberService.saveOrUpdateSocialMember(loginId, socialProvider, nickName, name);
        System.out.println("Naver User Saved: " + loginId + ", " + ", " + socialProvider + "," + nickName);
      } else if (!memberService.isSocialMemberExists(loginId) && memberService.isNickNameDuplicated(nickName)) {
        // 중복된 경우에 대한 처리를 여기에 추가
        // 예시로 로그에 출력하는 코드를 추가했습니다.
        System.out.println("NickName is duplicated: " + nickName);
        // 중복 처리 로직을 추가하거나 세션에 정보를 추가
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("duplicatedNickName", nickName);
        session.setAttribute("socialLoginId",loginId);
        session.setAttribute("Provider",socialProvider);
        throw new OAuth2AuthenticationException("Social login is blocked.");
      }
      System.out.println("Google User Saved: " + loginId + ", " + socialProvider + ", " + nickName);

    } else if (clientName.equals("kakao")) {
      // Kakao 로그인 사용자 정보 추출
      //아마 여기서 맵해쉬를쓴부분은 디버그를사용해서 어디에 엔티티정보가 담겨져있는지 확인후에 NAME이름을 정해줘야하는데
      // 프로퍼티스에담겨있는걸확인해서 네임이름은 저걸로꼭해줘야합니다.
      Map attributes = oAuth2User.getAttributes();
      Long id = (Long) attributes.get("id");
      loginId = String.valueOf(id);
      Map properties = (Map) attributes.get("properties");
      String nickName = (String) properties.get("nickname");
      String socialProvider = userRequest.getClientRegistration().getClientName();
      String name = (String) properties.get("name");
      if (memberService.isSocialMemberExists(loginId)) {
        // 이미 등록된 소셜 사용자인 경우에 대한 처리를 여기에 추가
        // 예시로 로그에 출력하는 코드를 추가했습니다.
        System.out.println("Social User Already Exists: " + loginId);
        // 중복 처리 로직을 추가하거나 세션에 정보를 추가
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("existingSocialUser", loginId);
      }
      if (!memberService.isSocialMemberExists(loginId) && !memberService.isNickNameDuplicated(nickName)) {
        // Save or update Naver information in the database
        memberService.saveOrUpdateSocialMember(loginId, socialProvider, nickName, name);
        System.out.println("Naver User Saved: " + loginId + ", " + ", " + socialProvider + "," + nickName);
      } else if (!memberService.isSocialMemberExists(loginId) && memberService.isNickNameDuplicated(nickName)) {
        // 중복된 경우에 대한 처리를 여기에 추가
        // 예시로 로그에 출력하는 코드를 추가했습니다.
        System.out.println("NickName is duplicated: " + nickName);
        // 중복 처리 로직을 추가하거나 세션에 정보를 추가
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("duplicatedNickName", nickName);
        session.setAttribute("socialLoginId",loginId);
        session.setAttribute("Provider",socialProvider);
        throw new OAuth2AuthenticationException("Social login is blocked.");
      }
    } else if (clientName.equals("Naver")) {
      // 네이버 로그인 사용자 정보 추출
      // 네이버 로그인 사용자 정보 추출
      Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("response");
      loginId = response.get("email").toString();
      String socialProvider = userRequest.getClientRegistration().getClientName();
      String nickname = response.get("nickname").toString();
      String name = response.get("name").toString();

      if (memberService.isSocialMemberExists(loginId)) {
        // 이미 등록된 소셜 사용자인 경우에 대한 처리를 여기에 추가
        // 예시로 로그에 출력하는 코드를 추가했습니다.
        System.out.println("Social User Already Exists: " + loginId);
        // 중복 처리 로직을 추가하거나 세션에 정보를 추가
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("existingSocialUser", loginId);
      }

      if (!memberService.isSocialMemberExists(loginId) && !memberService.isNickNameDuplicated(nickname)) {
        // Save or update Naver information in the database, 사용자 아이디가 등록되어있지않거나,닉네임이 중복되어있지않을떄
        memberService.saveOrUpdateSocialMember(loginId, socialProvider, nickname, name);
        System.out.println("Naver User Saved: " + loginId + ", " + ", " + socialProvider + "," + nickname);
      } else if (!memberService.isSocialMemberExists(loginId) && memberService.isNickNameDuplicated(nickname)) {
        // 중복된 경우에 대한 처리를 여기에 추가
        // 예시로 로그에 출력하는 코드를 추가했습니다.
        System.out.println("NickName is duplicated: " + nickname);
        // 중복 처리 로직을 추가하거나 세션에 정보를 추가
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("duplicatedNickName", nickname);
        session.setAttribute("socialLoginId",loginId);
        session.setAttribute("Provider",socialProvider);
        throw new OAuth2AuthenticationException("Social login is blocked.");
      }
    }

    return new SocialMember(loginId,"", new ArrayList<>(), oAuth2User.getAttributes());
  }

}
