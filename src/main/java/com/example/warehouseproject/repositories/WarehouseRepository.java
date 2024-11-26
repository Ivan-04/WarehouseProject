package com.example.warehouseproject.repositories;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository  extends JpaRepository<Warehouse, Integer> {

    List<Warehouse> findAll();

    Optional<Warehouse> findByWarehouseId(int id);

    Optional<Warehouse> findByName(String name);

    Warehouse findWarehouseEntityByName(String name);

}