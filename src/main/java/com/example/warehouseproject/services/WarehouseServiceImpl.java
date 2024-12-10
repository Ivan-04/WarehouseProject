package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.*;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.repositories.WarehouseRepository;
import com.example.warehouseproject.services.contracts.PartService;
import com.example.warehouseproject.services.contracts.WarehouseLogService;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import com.example.warehouseproject.services.contracts.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ConversionService conversionService;
    private final WarehousePartService warehousePartService;
    private final PartService partService;
    private final WarehouseLogService warehouseLogService;

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
    public Warehouse findWarehouseEntityById(int id){

        return warehouseRepository.findByWarehouseId(id).orElseThrow(
                () -> new EntityNotFoundException("Warehouse", id));
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


    @Override
    public void addPartToWarehouse(User user, String warehouseName, String partTitle, int quantityOfPart) {

        List<WarehousePartOutput> warehousePartOutputs = warehousePartService.findAllWarehousesParts();

        for (WarehousePartOutput warehousePartOutput : warehousePartOutputs) {
            if (warehousePartOutput.getPartName().equals(partTitle) &&
                    warehousePartOutput.getWarehouseName().equals(warehouseName)){
                Warehouse warehouse = warehouseRepository.findWarehouseEntityByName(warehouseName);
                Part part = partService.getPartEntity(partTitle);

                WarehousePart warehousePart = warehousePartService.findWarehousePart(warehouse, part);
                warehousePartService.changeTheQuantity(warehousePart, quantityOfPart);

                WarehouseLogInput warehouseLogInput = WarehouseLogInput.builder()
                        .user(user)
                        .warehouse(warehouse)
                        .part(part)
                        .action(Action.ADD)
                        .quantity(quantityOfPart)
                        .build();

                warehouseLogService.createWarehouseLog(warehouseLogInput);

            }else {

                Warehouse warehouse = warehouseRepository.findWarehouseEntityByName(warehouseName);
                Part part = partService.getPartEntity(partTitle);

                WarehouseLogInput warehouseLogInput = WarehouseLogInput.builder()
                        .user(user)
                        .warehouse(warehouse)
                        .part(part)
                        .action(Action.ADD)
                        .quantity(quantityOfPart)
                        .build();

                warehouseLogService.createWarehouseLog(warehouseLogInput);

                warehousePartService.createWarehouse(warehouse, part, quantityOfPart);
            }
        }

        if (warehousePartOutputs.isEmpty()){
            Warehouse warehouse = warehouseRepository.findWarehouseEntityByName(warehouseName);
            Part part = partService.getPartEntity(partTitle);

            warehousePartService.createWarehouse(warehouse, part, quantityOfPart);

            WarehouseLogInput warehouseLogInput = WarehouseLogInput.builder()
                    .user(user)
                    .warehouse(warehouse)
                    .part(part)
                    .action(Action.ADD)
                    .quantity(quantityOfPart)
                    .build();

            warehouseLogService.createWarehouseLog(warehouseLogInput);
        }

    }

    @Override
    public void removePartFromWarehouse(User user, String warehouseName, String partTitle, int quantityOfPart) {

        List<WarehousePartOutput> warehousePartOutputs = warehousePartService.findAllWarehousesParts();

        boolean isFounded = false;

        if (warehousePartOutputs.isEmpty()){
            throw new EntityNotFoundException("Part", "title", partTitle);
        }

        for (WarehousePartOutput warehousePartOutput : warehousePartOutputs) {
            if (warehousePartOutput.getPartName().equals(partTitle) &&
                    warehousePartOutput.getWarehouseName().equals(warehouseName)){
                Warehouse warehouse = warehouseRepository.findWarehouseEntityByName(warehouseName);
                Part part = partService.getPartEntity(partTitle);

                WarehousePart warehousePart = warehousePartService.findWarehousePart(warehouse, part);
                warehousePartService.removePartOfThisType(warehousePart, quantityOfPart);

                WarehouseLogInput warehouseLogInput = WarehouseLogInput.builder()
                        .user(user)
                        .warehouse(warehouse)
                        .part(part)
                        .action(Action.REMOVE)
                        .quantity(quantityOfPart)
                        .build();
                warehouseLogService.createWarehouseLog(warehouseLogInput);
                isFounded = true;
                break;
            }
        }

        if (!isFounded){
            throw new EntityNotFoundException("Part", "title", partTitle);
        }
    }
}
