package com.project.vaccine.repository;

import com.project.vaccine.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    @Query(value = "select * from position;", nativeQuery = true)
    List<Position> getAllPosition();
}
