package com.korea.Team5.kmapi;

import com.korea.Team5.movie.MovieInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KmapiController {
    private final KmapiService kmapiService;

    @GetMapping("/kmapi")
    public String videoList(Model model){
        List<MovieInfoDto> movieInfoList = this.kmapiService.videoListSaveDataBase();

        model.addAttribute("movieInfoList", movieInfoList);
        return "movie";
    }


}
