package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.WarehouseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseLogRepository extends JpaRepository<WarehouseLog, Integer> {

    List<WarehouseLog> findAll();

    WarehouseLog findByLogId(int id);

}
