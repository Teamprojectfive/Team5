package com.korea.Team5.theater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/theater")
public class TheaterController {

  private final TheaterService theaterService;


  @GetMapping("/information")
  public String information(){



    return "/Theater/theater_form";
  }

  @GetMapping("/detail")
  public String detail(Model model,@PathVariable("id") Integer id){

    Excel excel = this.theaterService.getExcel(id);

    model.addAttribute("excel",excel);



    return "/Theater/theater_detail";
  }
}
