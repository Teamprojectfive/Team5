package com.korea.Team5.movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String peopleNm;  // 스태프 이름
    private String peopleNmEn;  // 스태프 영어 이름
    private String staffRoleNm;  // 스태프 역할
}
