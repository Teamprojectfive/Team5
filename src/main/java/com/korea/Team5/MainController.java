package com.korea.Team5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {


     @GetMapping
      public String root(){
            return "redirect:/main";
      }


}
