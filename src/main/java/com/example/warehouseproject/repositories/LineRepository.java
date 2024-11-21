package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {

    List<Line> findAll();

    Optional<Line> findLineByLineId(int id);

    Optional<Line> findLineByNum(int num);

    Line findLineEntityByNum(int num);
}
