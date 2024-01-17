package com.korea.Team5.theater;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class GroupRegion {
  static final String CHUNG_CHEONG_GROUP_NAME = "대전/충청/세종";
  static final String KYEONG_SANG_GROUP_NAME = "부산/대구/경상";
  static final String JEON_LA_GROUP_NAME = "광주/전라";

  static final List<String> CHUNG_CHEONG_LIST = new ArrayList<>(Arrays.asList("대전시", "세종시", "충청북도", "충청남도"));
  static final List<String> KYEONG_SANG_LIST = new ArrayList<>(Arrays.asList("부산시", "대구시", "경상남도", "경상북도"));
  static final List<String> JEON_LA_LIST = new ArrayList<>(Arrays.asList("광주시", "전라북도", "전라남도"));

  static List<String> getGroupTargetList() {
    List<String> groupTargetList = new ArrayList<>();
    groupTargetList.addAll(CHUNG_CHEONG_LIST);
    groupTargetList.addAll(KYEONG_SANG_LIST);
    groupTargetList.addAll(JEON_LA_LIST);

    return groupTargetList;
  }

  static List<String> getGroupNameList() {
    List<String> groupNameList = new ArrayList<>();
    groupNameList.add(CHUNG_CHEONG_GROUP_NAME);
    groupNameList.add(KYEONG_SANG_GROUP_NAME);
    groupNameList.add(JEON_LA_GROUP_NAME);

    return groupNameList;
  }

}
