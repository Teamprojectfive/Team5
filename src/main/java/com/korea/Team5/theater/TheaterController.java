package com.korea.Team5.theater;

import com.korea.Team5.theater.entity.Excel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/theater")
public class TheaterController {

  private final TheaterService theaterService;




//  @GetMapping("/bigregion")
//  public String bigregion(Model model,@RequestParam String bigRegion,@RequestParam String smallRegion){
//
//    List<Excel> regionexcelList = this.theaterService.getExcelRegion(bigRegion, smallRegion);
//    model.addAttribute("region",regionexcelList);
//
//
//    return "/Theater/theater_form";
//  }






//  @GetMapping("/bigregion")
//  public String bigregion(Model model,@RequestParam String bigRegion,@RequestParam String smallRegion){
//
//    List<Excel> regionexcelList = this.theaterService.getExcelRegion(bigRegion, smallRegion);
//    model.addAttribute("region",regionexcelList);
//
//
//    return "/Theater/theater_form";
//  }

  @GetMapping("/detail")
  public String detail(Model model, @RequestParam String targetBigRegion, @RequestParam String targetSmallRegion) {
    List<Excel> regionList = theaterService.getExcelRegion(targetBigRegion, targetSmallRegion);

    // 필요한 작업 수행
    model.addAttribute("regionList", regionList);
    model.addAttribute("targetBigRegion", targetBigRegion);
    model.addAttribute("targetSmallRegion", targetSmallRegion);

    return "Theater/theater_detail";
  }

  @GetMapping("/smallRegion")
  public String smallRegion(Model model, @RequestParam(required = false) String targetBigRegion){
    if(targetBigRegion == null || targetBigRegion.isEmpty()) {
      targetBigRegion = "서울시";
    }
    List<String> smallList = this.theaterService.getTargetSmallRegion(targetBigRegion);
    List<String> bigList = this.theaterService.getBigRegion();
    model.addAttribute("smallList",smallList);
    model.addAttribute("bigRegionlList",bigList);
    model.addAttribute("bigRegion",targetBigRegion);

    return "/Theater/theater_form";
  }
  @GetMapping("/listdetail")
  public String listdetail(Model model,@RequestParam String region){


    // region에 해당하는 데이터의 name 속성값 가져오기
    Excel theaterName = theaterService.getExcelTheater(region);
    // 모델에 데이터 추가

    model.addAttribute("theaterName", theaterName);


    return "/Theater/theater_listdetail";
  }
  @GetMapping("/navermap")
  public String theatermap(Model model,@RequestParam String address){

    model.addAttribute("address",address);
    System.out.println(address);
    return  "/Theater/theaterNavermap";
  }

}