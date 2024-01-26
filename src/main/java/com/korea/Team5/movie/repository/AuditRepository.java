package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.Actor1;
import com.korea.Team5.movie.entity.Audit;
import com.korea.Team5.movie.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {


    Audit findByWatchGradeNmAndMovieInfo(String watchGradeNm, MovieInfo targetMovieInfo);


}
