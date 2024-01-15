package com.korea.Team5.theater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/theater")
public class TheaterController {

  @GetMapping("/information")
  public String information(){



    return "/Theater/theater_form";
  }
}
