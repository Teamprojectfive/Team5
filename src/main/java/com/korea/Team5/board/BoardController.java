package com.korea.Team5.board;


import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.board.article.Article;
import com.korea.Team5.board.article.ArticleForm;
import com.korea.Team5.board.article.ArticleService;
import com.korea.Team5.movie.MovieService;
import com.korea.Team5.movie.entity.MovieInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.security.Principal;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final MovieService movieService;
    private final BoardService boardService;
    private final MemberService memberService;
    private final ArticleService articleService;





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
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    public String selectPoster(@RequestParam(name = "selectPoster") String selectPoster,
                               @RequestParam(name = "title") String title,
                               @RequestParam(name = "content") String content,Principal principal,
                               Model model) {
        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("selectedPosterUrl", selectPoster);


        this.boardService.registerRoom(member,title, content,selectPoster);




        return "redirect:/board/movie";
    }




    @GetMapping("/article/detail/{id}")
    public String articledetail(@PathVariable("id")Integer id,Model model){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article",article);
        return "articleDetail";
    }


    @GetMapping("/article/list/{id}")
    public String articleList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,@PathVariable("id") Integer id) {
        Page<Article> articlePage = this.articleService.getListByBoard(page, id);
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board",board);

        model.addAttribute("articles", articlePage);
        return "articleList";
    }

    @GetMapping("/article/create")

    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    public String articlecreate(ArticleForm articleForm,@RequestParam Integer boardId,Model model) {
        Board board = this.boardService.getBoard(boardId);
        model.addAttribute("board",board);

        return "articleCreate";
    }

    @PostMapping("/article/create")

    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal,@RequestParam Integer boardId) {

        if (bindingResult.hasErrors()) {
            return "articleCreate";
        }
        Member member = memberService.getMember(principal.getName());

        Board board = boardService.getBoard(boardId);
        this.articleService.create(articleForm.getTitle(), articleForm.getContent(), member,board);

        return "redirect:/board/article/list/" + boardId;
    }

    @GetMapping("/article/modify/{id}")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")

    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if (!article.getMember().getNickName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이없습니다.");
        }
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        return "articleCreate";
    }

    @PostMapping("/article/modify/{id}")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")

    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "articleCreate";
        }
        Article article = this.articleService.getArticle(id);
        if (!article.getMember().getNickName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이없습니다.");
        }
        this.articleService.modify(article, articleForm.getTitle(), articleForm.getContent());
        return String.format("redirect:/board/article/list");

    }



}



