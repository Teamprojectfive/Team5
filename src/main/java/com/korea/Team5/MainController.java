package com.korea.Team5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {


  @GetMapping
  public String root() {
    return "redirect:/main";
  }

  @GetMapping("template-test")
  public String test(Model model) {

    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);

    model.addAttribute("list", list);

    return "template_test";
  }
}
