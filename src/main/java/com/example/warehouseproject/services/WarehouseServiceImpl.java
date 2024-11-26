package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.repositories.WarehouseRepository;
import com.example.warehouseproject.services.contracts.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ConversionService conversionService;

    @Override
    public List<WarehouseOutput> findAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream().map(warehouse -> conversionService.convert(warehouse, WarehouseOutput.class))
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseOutput findWarehouseById(int id){
        Warehouse warehouse = warehouseRepository.findByWarehouseId(id).orElseThrow(
                () -> new EntityNotFoundException("Warehouse", id));

        return conversionService.convert(warehouse, WarehouseOutput.class);
    }

    @Override
    public WarehouseOutput findWarehouseByName(String title){
        Warehouse warehouse = warehouseRepository.findByName(title).orElseThrow(
                () -> new EntityNotFoundException("Warehouse", "name", title));

        return conversionService.convert(warehouse, WarehouseOutput.class);
    }

    @Override
    public WarehouseOutputId createWarehouse(WarehouseInput warehouseInput) {

        Warehouse warehouse = Warehouse.builder()
                .name(warehouseInput.getName())
                .build();

        Warehouse existingWarehouse = warehouseRepository.findWarehouseEntityByName(warehouseInput.getName());

        if (existingWarehouse != null) {
            throw new DuplicateEntityException("Warehouse", "name", warehouseInput.getName());
        }

        warehouseRepository.save(warehouse);

        return conversionService.convert(warehouse, WarehouseOutputId.class);
    }

}
