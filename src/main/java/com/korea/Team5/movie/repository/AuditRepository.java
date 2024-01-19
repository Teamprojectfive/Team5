package com.korea.Team5.movie.repository;

import com.korea.Team5.movie.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
