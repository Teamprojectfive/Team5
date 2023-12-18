package com.korea.Team5.USER;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer UserId;

  @Column(unique = true)
  private String loginId;

  private String password;

  @Column(unique = true)
  private String nickName;

  @Column(unique = true)
  private String phone;

  private String email;

}
