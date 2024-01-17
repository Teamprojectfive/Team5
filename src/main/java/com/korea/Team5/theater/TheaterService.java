package com.korea.Team5.theater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

  private final TheaterRepository theaterRepository;
  private final ExcelRepository excelRepository;



  public List<Excel> getExcelRegion(String bigRegion,String smallRegion){
    List<Excel> excelList = this.excelRepository.findByBigRegionAndSmallRegion(bigRegion, smallRegion);
    System.out.println(excelList);
    return excelList;
  }

  public List<String> getBigRegion(){
    List<String> bigRegionList = this.excelRepository.findDistinctBigRegionList();
    List<String> groupedBigRegionList = new ArrayList<>();

    for (String bigRegion : bigRegionList) {
      if (isTargetRegion(bigRegion)) {
        // 대상 지역인 경우 그룹화하여 추가
        groupedBigRegionList.add(groupRegions(bigRegion));
      } else {
        // 대상이 아닌 경우 그대로 추가
        groupedBigRegionList.add(bigRegion);
      }
    }

    List<String> uniqueGroupedBigRegionList = new ArrayList<>(new LinkedHashSet<>(groupedBigRegionList));
    return uniqueGroupedBigRegionList;
  }

  private boolean isTargetRegion(String region) {
    // 대상 지역인지 확인하는 로직
    List<String> targetRegions = Arrays.asList("대전시", "세종시", "충청북도", "충청남도", "부산시", "대구시", "경상남도", "경상북도", "광주시", "전라북도", "전라남도");
    return targetRegions.contains(region);
  }

  private String groupRegions(String region) {
    // 그룹화 로직
    // 여기에서 필요에 따라 그룹화하여 원하는 형태로 조합
    if (region.contains("대전시") || region.contains("세종시") || region.contains("충청북도") || region.contains("충청남도")) {
      return "대전/충청/세종";
    } else if (region.contains("부산시") || region.contains("대구시") || region.contains("경상남도") || region.contains("경상북도")) {
      return "부산/대구/경상";
    } else if (region.contains("광주시") || region.contains("전라북도") || region.contains("전라남도")) {
      return "광주/전라";
    }
    // 그룹화되지 않은 경우 그대로 반환
    return region;
  }

  public List<String> getSmallRegion(String targetBigRegion){


    List<String> smallRegionList = this.excelRepository.findDistinctSmallRegionListForBigRegion(targetBigRegion);

    return smallRegionList;
  }




}

