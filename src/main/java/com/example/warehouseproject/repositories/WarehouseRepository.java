package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository  extends JpaRepository<Warehouse, Integer> {

    @Query("SELECT w FROM Warehouse w " +
            "WHERE (:name IS NULL OR w.name like :name) ")
    Page<Warehouse> findWarehousesByMultipleFields(
            @Param("name") String name,
            Pageable pageable);

    List<Warehouse> findAll();

    Optional<Warehouse> findByWarehouseId(int id);

    Optional<Warehouse> findByName(String name);

    Warehouse findWarehouseEntityByName(String name);

    Warehouse findWarehouseEntityByWarehouseId(int id);

}
