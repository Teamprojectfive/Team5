package com.korea.Team5.theater;

import lombok.Getter;

import java.util.*;

@Getter
public class GroupRegion {

  static final private Map<String, List<String>> groupMap;

  static {
    String chungcheongGroup = "대전/충청/세종";
    String kyeongsangGroup = "부산/대구/경상";
    String jeonlaGroup = "광주/전라";

    List<String> chungcheongList = new ArrayList<>(Arrays.asList("대전시", "세종시", "충청북도", "충청남도"));
    List<String> kyeongsangList = new ArrayList<>(Arrays.asList("부산시", "대구시", "경상남도", "경상북도"));
    List<String> jeonlaList = new ArrayList<>(Arrays.asList("광주시", "전라북도", "전라남도"));

    groupMap = new HashMap<>();
    groupMap.put(chungcheongGroup, chungcheongList);
    groupMap.put(kyeongsangGroup, kyeongsangList);
    groupMap.put(jeonlaGroup, jeonlaList);

  }

  static Map<String, List<String>> getGroupMap() {
    return groupMap;
  }

  static List<String> getGroupTargetList() {
    List<String> groupTargetList = new ArrayList<>();

    Set<String> keySet = groupMap.keySet();
    for(String key : keySet) {
      List<String> groupList = groupMap.get(key);
      groupTargetList.addAll(groupList);
    }
    return groupTargetList;
  }

  static List<String> getGroupNameList() {
    return new ArrayList<>(groupMap.keySet());
  }

  static List<String> getGroupList(String groupName) {
    return groupMap.get(groupName);
  }

}
