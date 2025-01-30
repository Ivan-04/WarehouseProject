package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehousePart;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehousePartsRepository extends JpaRepository<WarehousePart, Integer> {

    List<WarehousePart> findAll();

    WarehousePart findByWarehouseAndPart(Warehouse warehouse, Part part);

//    @Query("SELECT wp FROM WarehousePart wp " +
//            "WHERE (:title IS NULL OR LOWER(wp.part.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
//            "ORDER BY wp.part.title ASC")
//    Page<WarehousePart> findWarehousePartsByMultipleFields(
//            @Param("title") String title,
//            Pageable pageable);

//    @Query("SELECT wp FROM WarehousePart wp " +// Свързваме складовата част с нейната част
//            "WHERE wp.part = :part")
//    Page<WarehousePart> findWarehousePartsByMultipleFields(
//            @Param("part") Part part,
//            Pageable pageable);

//    @Query("SELECT wp FROM WarehousePart wp " +
//            "JOIN wp.part p " +  // Свързваме с Part
//            "WHERE wp.part = :part " +
//            "ORDER BY wp.quantity ASC")  // Правилно сортиране
//    Page<WarehousePart> findWarehousePartsByMultipleFields(
//            @Param("part") Part part,
//            Pageable pageable);

//    @Query("SELECT wp FROM WarehousePart wp " +
//            "WHERE wp.part = :part " +
//            "ORDER BY wp.part.title ASC")  // Сортираме по title на Part
//    Page<WarehousePart> findWarehousePartsByMultipleFields(
//            @Param("part") Part part,
//            Pageable pageable);


}
