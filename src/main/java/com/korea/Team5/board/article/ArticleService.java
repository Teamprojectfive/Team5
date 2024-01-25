package com.korea.Team5.board.article;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;



    public void create(String title, String content, Member member, Board board){
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        article.setBoard(board);

        article.setMember(member);


        this.articleRepository.save(article);
    }
    public List<Article> list(){
        return this.articleRepository.findAll();
    }

    public Page<Article> getListByBoard(int page, Integer id){
        Pageable pageable = PageRequest.of(page, 10);
        return this.articleRepository.findByBoardId(id, pageable);
    }

    public void modify(Article article, String title, String content){
        article.setTitle(title);
        article.setContent(content);
        this.articleRepository.save(article);
    }


    public Article getArticle(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }
}