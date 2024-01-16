package com.korea.Team5.theater;


import com.korea.Team5.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
  public Excel getExcel(Integer id){
    Optional<Excel> excel = this.excelRepository.findById(id);
    if (excel.isPresent()) {
      return excel.get();
    } else {
      throw new DataNotFoundException("excel not found");
    }

  }

//  public Excel getRegion(){
//    List<Excel> excelList = this.excelRepository.findAll();
//    excelList.get(g)
//
//
//
//
//
//  }






}
