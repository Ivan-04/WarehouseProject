package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Action;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehouseLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WarehouseLogRepository extends JpaRepository<WarehouseLog, Integer> {

    @Query("SELECT w FROM WarehouseLog w " +
            "WHERE (:user IS NULL OR w.user.email like :user) " +
            "AND (:warehouse IS NULL OR w.warehouse.name like :warehouse)"+
            "AND (:part IS NULL OR w.part.title like :part)" +
            "AND (:action IS NULL OR w.action = :action)" +
            "AND (:quantity IS NULL OR w.quantity = :quantity)" +
            "AND (:timestamp IS NULL OR w.timestamp = :timestamp)"+
            "AND (:description IS NULL OR w.description like :description)")
    Page<WarehouseLog> findWarehouseLogsByMultipleFields(
            @Param("user") String email,
            @Param("warehouse") String warehouseName,
            @Param("part") String partName,
            @Param("action") Action action,
            @Param("quantity") Integer quantity,
            @Param("timestamp") LocalDateTime timestamp,
            @Param("description") String description,
            Pageable pageable);

    List<WarehouseLog> findAll();

    WarehouseLog findByLogId(int id);

}
