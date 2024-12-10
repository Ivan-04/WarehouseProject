package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {

    List<Part> findAll();

    Optional<Part> findPartByPartId(int id);

    Optional<Part> findPartByTitle(String title);

    Part findPartEntityByTitle(String title);

    Part findPartEntityByPartId(int id);

    @Query("SELECT p FROM Part p " +
            "WHERE (:title IS NULL OR p.title like :title) " +
            "AND (:description IS NULL OR p.description like :description)")
    Page<Part> findPartsByMultipleFields(
            @Param("title") String title,
            @Param("description") String description,
            Pageable pageable);
}
