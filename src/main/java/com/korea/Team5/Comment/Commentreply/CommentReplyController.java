package com.korea.Team5.Comment.Commentreply;

import com.korea.Team5.Comment.Comment;
import com.korea.Team5.Comment.CommentService;
import com.korea.Team5.USER.Member;
import com.korea.Team5.USER.MemberService;
import com.korea.Team5.board.article.Article;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reply")
public class CommentReplyController {

  private final CommentReplyService commentReplyService;
  private final CommentService commentService;
  private final MemberService memberService;

  @GetMapping("/list")
  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  public String list() {


    return "articleDetail";
  }

  @PostMapping("/create/{id}")
  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  public String create(@Valid CommentReplyForm commentReplyForm, BindingResult bindingResult, Model model, @PathVariable("id") Integer commentId, Principal principal) {
    Member member = this.memberService.getMember(principal.getName());
    Comment comment = this.commentService.getComment(commentId);
       Article article = comment.getArticle();
        Integer articleId = article.getId();
    if(bindingResult.hasErrors()){
      model.addAttribute("article",article);
      return "articleDetail";
    }
      this.commentReplyService.create(member,commentReplyForm.getContent() ,comment);


    return String.format("redirect:/board/article/detail/%s", articleId);
  }

  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/delete/{id}")
  public String delete(Principal principal, @PathVariable("id") Integer id, @RequestParam Integer articleId){

    CommentReply commentReply = this.commentReplyService.getreply(id);
    if(!commentReply.getMember().getLoginId().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }
    this.commentReplyService.delete(commentReply);
    return "redirect:/board/article/detail/" + articleId;
  }

  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/modify/{id}")
  public String modify(@PathVariable("id") Integer replyId, CommentReplyForm commentReplyForm, @RequestParam Integer articleId, Principal principal, BindingResult bindingResult){

    if (bindingResult.hasErrors()) {
      return "articleDetail";
    }
    CommentReply commentReply = this.commentReplyService.getreply(replyId);
    if (!commentReply.getMember().getLoginId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    this.commentReplyService.modify(commentReply, commentReplyForm.getContent());
    return String.format("redirect:/board/article/detail/%s", articleId);
  }
  @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
  @GetMapping("/vote/{id}")
  public String vote(Principal principal, @PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
    CommentReply commentReply = this.commentReplyService.getreply(id);
    Member member = this.memberService.getMember(principal.getName());
    boolean ActionCheck = this.commentReplyService.vote(commentReply,member);
    redirectAttributes.addFlashAttribute("ActionCheck",ActionCheck);
    return String.format("redirect:/board/article/detail/%s", commentReply.getComment().getArticle().getId());
  }
}
