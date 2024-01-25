package com.korea.Team5.Comment;

import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.board.article.Article;
import com.korea.Team5.board.article.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Comment")
public class CommentController {


  private final MemberService memberService;
  private final CommentService commentService;
  private final ArticleService articleService;



  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create/{id}")
  public String create(@Valid CommentCreateForm commentCreateForm, Model model, Principal principal, @PathVariable("id") Integer articleId, BindingResult bindingResult){
    Member member = this.memberService.getMember(principal.getName());
    Article article = this.articleService.getArticle(articleId);
    if(bindingResult.hasErrors()){
      model.addAttribute("article",article);
      return "articleDetail";
    }
    this.commentService.create(member, commentCreateForm.getContent(),article);

    model.addAttribute("member",member);


    return String.format("redirect:/board/article/detail/%s", articleId);
  }
}
