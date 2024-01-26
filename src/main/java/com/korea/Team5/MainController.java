package com.korea.Team5;

import com.korea.Team5.movie.MovieService;
import com.korea.Team5.movie.entity.Movie;
import com.korea.Team5.theater.TheaterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller

public class MainController {

  private final MovieService movieService;
  private final TheaterService theaterService;


  @GetMapping
  public String root() {
    return "redirect:/main";
  }







  @GetMapping("/main")
  public String main(Model model, @RequestParam(value = "page", defaultValue = "0") int page, HttpSession session) {
    Page<Movie> paging = this.movieService.mainList(page);
    model.addAttribute("paging", paging);
    session.removeAttribute("verificationCode");
    session.removeAttribute("phoneverificationCode");
    // getExcelRegion 메서드를 사용하여 엑셀 데이터를 가져옴
    List<String> bigRegionlList = theaterService.getBigRegion();




    return "main";
  }

  @GetMapping("/check-login-status")
  @ResponseBody
  public Map<String, Object> checkLoginStatus(Authentication authentication) {
    Map<String, Object> response = new HashMap<>();
    response.put("loggedIn", authentication != null && authentication.isAuthenticated());
    return response;
  }



}
