package com.korea.Team5.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {


  Board findAllById(Integer id);
}
