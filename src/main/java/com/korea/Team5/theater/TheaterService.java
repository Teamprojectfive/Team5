package com.korea.Team5.theater;

import com.korea.Team5.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterService {

  private final TheaterRepository theaterRepository;
  private final ExcelRepository excelRepository;

  public Theater getTheater(Integer id) {
    Optional<Theater> theater = this.theaterRepository.findById(id);
    if (theater.isPresent()) {
      return theater.get();
    } else {
      throw new DataNotFoundException("theater not found");
    }
  }

  public Excel getExcel(Integer id) {
    Optional<Excel> excel = this.excelRepository.findById(id);
    if (excel.isPresent()) {
      return excel.get();
    } else {
      throw new DataNotFoundException("Excel not found");
    }
  }


  public List<Excel> getExcelRegion(String bigRegion,String smallRegion){
    List<Excel> excelList = this.excelRepository.findByBigRegionAndSmallRegion(bigRegion, smallRegion);
    System.out.println(excelList);
    return excelList;
  }

  public List<String> getBigRegion(){

    List<String> bigRegionList = this.excelRepository.findDistinctBigRegionList();
    System.out.println("sdf");
    return bigRegionList;
  }

  public List<String> getSmallRegion(String targetBigRegion){


    List<String> smallRegionList = this.excelRepository.findDistinctSmallRegionListForBigRegion(targetBigRegion);

    return smallRegionList;
  }




}

