package com.korea.Team5.Comment;

import com.korea.Team5.USER.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public void create(Member member,String content){
    Comment comment = new Comment();
    comment.setContent(content);


    this.commentRepository.save(comment);
  }
}
