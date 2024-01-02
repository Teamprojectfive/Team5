package com.korea.Team5.kmapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {
    Optional<MovieInfo> findByTitle(String title);
}
