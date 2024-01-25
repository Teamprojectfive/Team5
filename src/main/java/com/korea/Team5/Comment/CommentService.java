package com.korea.Team5.Comment;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.board.article.Article;
import com.korea.Team5.board.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;

  public void create(Member member, String content, Article article){
    Comment comment = new Comment();
    comment.setContent(content);
    comment.setCreateDate(LocalDateTime.now());
    comment.setMember(member);
    comment.setArticle(article);


    this.commentRepository.save(comment);
  }

  public void delete(Comment comment){

    this.commentRepository.delete(comment);
  }

  public Comment getComment(Integer commentId){
    Optional<Comment> comment = this.commentRepository.findById(commentId);
    if (comment.isPresent()) {
      return comment.get();
    } else {
      throw new DataNotFoundException("comment not found");
    }

  }

  public void modify(Comment comment,String content){

    comment.setContent(content);
    comment.setModifyDate(LocalDateTime.now());
    this.commentRepository.save(comment);
  }
}
