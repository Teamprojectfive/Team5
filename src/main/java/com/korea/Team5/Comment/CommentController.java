package com.korea.Team5.Comment;

import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.board.article.Article;
import com.korea.Team5.board.article.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Comment")
public class CommentController {


  private final MemberService memberService;
  private final CommentService commentService;
  private final ArticleService articleService;


  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/create/{id}")
  public String create(@Valid CommentCreateForm commentCreateForm, Model model, Principal principal, @PathVariable("id") Integer articleId, BindingResult bindingResult) {
    Member member = this.memberService.getMember(principal.getName());
    Article article = this.articleService.getArticle(articleId);
    if (bindingResult.hasErrors()) {
      model.addAttribute("article", article);
      return "ArticleandBoard/articleDetail";
    }
    this.commentService.create(member, commentCreateForm.getContent(), article);

    model.addAttribute("member", member);


    return String.format("redirect:/board/article/detail/%s", articleId);
  }

  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/delete/{id}")
  public String delete(Principal principal, @PathVariable("id") Integer id, @RequestParam Integer articleId) {

    Comment comment = this.commentService.getComment(id);
    if (!comment.getMember().getLoginId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }
    this.commentService.delete(comment);
    return "redirect:/board/article/detail/" + articleId;

  }

  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/modify/{id}")
  public String modify(@RequestParam Integer articleId, Principal principal, @PathVariable("id") Integer id, CommentCreateForm commentCreateForm, @RequestParam String newcontent,
                       BindingResult bindingResult) {
    Comment comment = this.commentService.getComment(id);
    if (bindingResult.hasErrors()) {
      return "ArticleandBoard/articleDetail";
    }
    if (!comment.getMember().getLoginId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }

    this.commentService.modify(comment, newcontent);

    return "redirect:/board/article/detail/" + articleId;
  }
  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/vote/{id}")
  public String vote(Principal principal, @PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
    Comment comment = this.commentService.getComment(id);
    Member member = this.memberService.getMember(principal.getName());
    boolean ActionCheck = this.commentService.vote(comment,member);
    redirectAttributes.addFlashAttribute("ActionCheck",ActionCheck);
    return String.format("redirect:/board/article/detail/%s", comment.getArticle().getId());
  }
}