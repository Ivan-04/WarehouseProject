package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {

    List<Part> findAll();

    Optional<Part> findPartByPartId(int id);

    Optional<Part> findPartByTitle(String title);

    Part findPartEntityByTitle(String title);

}
