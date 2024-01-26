package com.korea.Team5.board;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.movie.entity.MovieInfo;
import com.korea.Team5.movie.repository.MovieInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;


    public List<Board> boardList() {
        return this.boardRepository.findAll();
    }

    // 방 등록 메서드
    public void registerRoom()
     {
        Board board = new Board();
        this.boardRepository.save(board);
    }


}