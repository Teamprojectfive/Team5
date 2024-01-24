package com.korea.Team5.board;

import com.korea.Team5.USER.MemberService;
import com.korea.Team5.movie.MovieService;
import com.korea.Team5.movie.entity.MovieInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final MovieService movieService;
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/movie")
    public String list(Model model, @RequestParam(name = "posterUrl", required = false) String posterUrl) {
        List<MovieInfo> movieInfoList = this.movieService.infoList();
        List<Board> boardList = this.boardService.boardList();
        if (posterUrl != null) {
            model.addAttribute("selectedPosterUrl", posterUrl);
        }


        model.addAttribute("boardList", boardList);
        model.addAttribute("movieInfoList", movieInfoList);

        return "boardList";
    }

    @GetMapping("/create")
    public String showCreateBoardPage(Model model) {
        List<MovieInfo> movieInfoList = this.movieService.infoList();
        model.addAttribute("movieInfoList", movieInfoList);
        return "boardCreate";
    }

    @PostMapping("/create")
    public String selectPoster(@RequestParam(name = "selectPoster") String selectPoster,
                               @RequestParam(name = "title") String title,
                               @RequestParam(name = "content") String content,
                               Model model) {

        model.addAttribute("selectedPosterUrl", selectPoster);
        this.boardService.registerRoom(title, content,selectPoster);

        return "redirect:/board/movie";
    }

    @GetMapping("/listdetail")
    public String listdetail(Model model,@RequestParam Integer boardId){
        Board board = this.boardService.getBoard(boardId);
        model.addAttribute("board",board);

        return "boardListdetail";
    }


}
