package com.korea.Team5.board;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
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
  public void registerRoom(Member member, String title, String content, String posterUrl) {

    Board board = new Board();
    board.setTitle(title);
    board.setContent(content);
    board.setPosterUrl(posterUrl);
    board.setMember(member);


    this.boardRepository.save(board);
  }

  public Board getBoard(Integer boardId) {

    Optional<Board> board = this.boardRepository.findById(boardId);
    if (board.isPresent()) {
      return board.get();
    } else {
      throw new DataNotFoundException("board not found");
    }
  }


}