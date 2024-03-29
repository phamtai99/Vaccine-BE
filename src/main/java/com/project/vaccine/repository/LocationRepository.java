package com.project.vaccine.repository;

import com.project.vaccine.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Integer> {

    /**
     *  Hiển thi ra danh sách và dung Query
     */

    @Query(
            value = "SELECT * FROM location;",
            nativeQuery = true)
    List<Location> findAllLocation();

}
