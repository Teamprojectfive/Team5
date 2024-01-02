package com.korea.Team5.kmapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieInfoController {
    private final MovieInfoService movieInfoService;

    @GetMapping("/movieInfo")
    public String videoList(Model model){
        List<MovieInfo> movieInfoList = this.movieInfoService.videoListSaveDataBase();
        model.addAttribute("movieInfoList", movieInfoList);
        return "movie";
    }
}
