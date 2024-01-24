package com.korea.Team5.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;

    public List<Board> boardList() {
        return this.boardRepository.findAll();
    }

    // 방 등록 메서드
    public void registerRoom(String title, String content, String posterUrl) {

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setPosterUrl(posterUrl);



        this.boardRepository.save(board);
    }

    public Board getBoard(Integer id){

        Board board = this.boardRepository.findAllById(id);

        return board;
    }

}