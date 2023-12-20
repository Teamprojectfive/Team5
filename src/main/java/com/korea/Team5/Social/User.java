package com.korea.Team5.Social;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
  @Id
    private String socialId;
    private String email;
    private String nickname;
    private String name;

}
