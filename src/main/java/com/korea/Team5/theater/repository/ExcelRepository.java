package com.korea.Team5.theater.repository;

import com.korea.Team5.theater.entity.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcelRepository extends JpaRepository<Excel,Integer> {
  List<Excel> findByBigRegionAndSmallRegion(String bigRegion,String smallRegion);
  List<Excel> findAllByBigRegionAndSmallRegion(String bigRegion, String smallRegion);

  // region에 해당하는 데이터 가져오기
  Excel findAllByName(String name);

  @Query("SELECT DISTINCT e.bigRegion FROM Excel e")
  List<String> findDistinctBigRegionList();

  @Query("SELECT DISTINCT e.smallRegion FROM Excel e WHERE e.bigRegion = :targetBigRegion")
  List<String> findDistinctSmallRegionListForBigRegion(@Param("targetBigRegion") String targetBigRegion);
  @Query("SELECT e FROM Excel e WHERE e.bigRegion IN :bigRegionList AND e.smallRegion = :targetSmallRegion")
  List<Excel> findAllByBigRegionListAndSmallRegion(
          @Param("bigRegionList") List<String> bigRegionList,
          @Param("targetSmallRegion") String targetSmallRegion
  );

}