package com.korea.Team5.Email;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
  public String sendVerificationCodeSMS(String phone) {
    // 인증 코드 생성 (여기에서는 간단하게 난수로 생성)
    String verificationCode = String.valueOf((int) (Math.random() * 9000) + 1000);
    // 이 부분을 변경하여 콘솔에 출력하거나 로깅할 수 있습니다.
    System.out.println("Verification code for phone number " + phone + ": " + verificationCode);

    // 이메일 전송 로직은 생략합니다.
    return verificationCode;
  }
}
