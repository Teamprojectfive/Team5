package com.korea.Team5.Dummy;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface dummyMovieRepository extends JpaRepository<dummyMovie, Integer> {
}
