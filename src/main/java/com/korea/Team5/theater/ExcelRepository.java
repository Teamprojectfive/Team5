package com.korea.Team5.theater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcelRepository extends JpaRepository<Excel,Integer> {
  List<Excel> findByBigRegionAndSmallRegion(String bigRegion,String smallRegion);

  @Query("SELECT DISTINCT e.bigRegion FROM Excel e")
  List<String> findDistinctBigRegionList();


  @Query("SELECT DISTINCT e.smallRegion FROM Excel e WHERE e.bigRegion = :targetBigRegion")
  List<String> findDistinctSmallRegionListForBigRegion(@Param("targetBigRegion") String targetBigRegion);

}
