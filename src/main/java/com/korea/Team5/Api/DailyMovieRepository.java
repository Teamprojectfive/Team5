package com.korea.Team5.Api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMovieRepository extends JpaRepository<DailyMovie, Long> {
    boolean existsByTargetDtAndMovieCd(String targetDt, String movieCd);

    void deleteAllByTargetDt(String targetDt);
}
