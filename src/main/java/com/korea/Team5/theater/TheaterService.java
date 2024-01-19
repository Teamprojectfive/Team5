package com.korea.Team5.theater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TheaterService {

  private final TheaterRepository theaterRepository;
  private final ExcelRepository excelRepository;

  public List<Excel> getExcelRegion(String targetBigRegion, String targetSmallRegion) {

    String smallRegion = getSmallRegionByCombinedRegion(targetSmallRegion);
    List<String> bigRegionList = GroupRegion.getGroupList(targetBigRegion);
    List<Excel> result = new ArrayList<>();
    if (bigRegionList != null && !bigRegionList.isEmpty()) {
      for (String bigRegion : bigRegionList) {
        List<Excel> tmp = excelRepository.findAllByBigRegionAndSmallRegion(bigRegion, smallRegion);
        result.addAll(tmp);
      }
    } else {
      List<Excel> tmp = excelRepository.findAllByBigRegionAndSmallRegion(targetBigRegion, smallRegion);
      result.addAll(tmp);
      // bigRegionList가 null이거나 비어있을 때의 로직 추가
      // 예시: 특정 기본값이나 다른 방식으로 데이터를 가져오거나 처리
    }

    return result;
  }
  public List<Excel> allExcel(){
    return this.excelRepository.findAll();
  }

  public Excel getExcelTheater(String region) {

    Excel excelDataList = excelRepository.findAllByName(region);


    return excelDataList;
  }


  public List<String> getBigRegion() {
    List<String> bigRegionList = this.excelRepository.findDistinctBigRegionList();
    List<String> groupedBigRegionList = new ArrayList<>();

    for (String bigRegion : bigRegionList) {
      if (isTargetGroupRegion(bigRegion)) {
        // 대상 지역인 경우 그룹화하여 추가
        groupedBigRegionList.add(getBigRegionGroupName(bigRegion));
      } else {
        // 대상이 아닌 경우 그대로 추가
        groupedBigRegionList.add(bigRegion);
      }
    }

    List<String> uniqueGroupedBigRegionList = new ArrayList<>(new LinkedHashSet<>(groupedBigRegionList));
    return uniqueGroupedBigRegionList;
  }

  private boolean isTargetGroupRegion(String region) {
    // 대상 지역인지 확인하는 로직
    return GroupRegion.getGroupTargetList().contains(region);
  }

  // 그룹에 해당하는 samllRegionList 가져오기
  private List<String> getSmallRegionListByBigRegionGroup(List<String> regionGroup) { // regionGroup -> 대상 그룹
    List<String> groupSmallList = new ArrayList<>();

    for (String bigRegion : regionGroup) { // reginGroup 안에서 bigRegion 하나씩 꺼냄 (예를 들면 대전/충청/세종의 경우 -> [대전시, 충청남도, 충청북도, 세종시]
      List<String> smallRegionList = this.excelRepository.findDistinctSmallRegionListForBigRegion(bigRegion); // 해당 bigRegion의 smallRegion 가져옴
      groupSmallList.addAll(smallRegionList); // 합침
    }

    return groupSmallList;
  }

  // 그룹 네임에 맞는 bigRegion 리스트
  private List<String> getBigRegionGroupList(String groupName) {
    return GroupRegion.getGroupList(groupName);
  }

  // bigRegion이 그룹이면 그룹 네임 아니면 그냥 원래 이름 반환
  private String getBigRegionGroupName(String region) {

    Map<String, List<String>> groupMap = GroupRegion.getGroupMap();

    for (String key : groupMap.keySet()) {
      if (groupMap.get(key).contains(region)) {
        return key;
      }
    }
    return region;

  }


  // bigRegion에 해당하는 smallRegion 리스트 구하기
  public List<String> getTargetSmallRegion(String targetBigRegion) {

    if(!GroupRegion.getGroupNameList().contains(targetBigRegion)) {
      return this.excelRepository.findDistinctSmallRegionListForBigRegion(targetBigRegion);
    }

    List<String> resultList = new ArrayList<>();
    List<String> regionList = GroupRegion.getGroupList(targetBigRegion);

    for (String region : regionList) {
      List<String> smallRegionList = this.excelRepository.findDistinctSmallRegionListForBigRegion(region);
      if (GroupRegion.getCityList().contains(region)) {
        smallRegionList = getCombinedSmallRegionByCity(region);
      }
      resultList.addAll(smallRegionList);
    }


    return resultList;
  }

  private List<String> getCombinedSmallRegionByCity(String cityName) {
    List<String> citySmallRegion = this.excelRepository.findDistinctSmallRegionListForBigRegion(cityName);
    List<String> combinedSmallRegion = new ArrayList<>();
    for (String region : citySmallRegion) {
      String combinedRegion = cityName + " " + region;
      combinedSmallRegion.add(combinedRegion);
    }
    return combinedSmallRegion;
  }

  private String getSmallRegionByCombinedRegion(String combinedRegion) {
    final int COMBINED_RESULT_COUNT = 2;
    final int SMALL_REGION_IDX = 1;
    String[] regionBits = combinedRegion.split(" ");
    if(regionBits.length != COMBINED_RESULT_COUNT) {
      return combinedRegion;
    }

    return regionBits[SMALL_REGION_IDX];

  }

}

