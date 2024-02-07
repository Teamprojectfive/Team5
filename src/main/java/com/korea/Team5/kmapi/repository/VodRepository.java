package com.korea.Team5.kmapi.repository;

import com.korea.Team5.kmapi.entity.Vod;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VodRepository extends JpaRepository<Vod, Integer> {


}
