package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehousePart;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehousePartsRepository extends JpaRepository<WarehousePart, Integer> {

    List<WarehousePart> findAll();

    WarehousePart findByWarehouseAndPart(Warehouse warehouse, Part part);
}
