package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.exceptions.InvalidDataException;
import com.example.warehouseproject.exceptions.UnauthorizedOperationException;
import com.example.warehouseproject.models.*;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.repositories.WarehouseRepository;
import com.example.warehouseproject.services.contracts.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ConversionService conversionService;
    private final WarehousePartService warehousePartService;
    private final PartService partService;
    private final WarehouseLogService warehouseLogService;
    private final UserService userService;


    @Override
    public Page<Warehouse> getWarehousesWithFilters(String name, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        String nameLike = name != null ? "%" + name + "%" : null;
        return warehouseRepository.findWarehousesByMultipleFields(nameLike, pageable);
    }

    @Override
    public Map<Part, Integer> getAllPartsOfWarehouse(int id){
        Warehouse warehouse = warehouseRepository.findWarehouseEntityByWarehouseId(id);
        List<WarehousePartOutput> warehousePartList = warehousePartService
                .findAllWarehousesParts()
                .stream()
                .filter(warehousePartOutput -> (warehousePartOutput.getWarehouseName().equals(warehouse.getName())))
                .toList();

        Map<Part, Integer> map = new HashMap<>();

        for(WarehousePartOutput warehousePartOutput : warehousePartList){
            Part part = partService.getPartEntity(warehousePartOutput.getPartName());
            map.put(part, warehousePartOutput.getQuantity());
        }
        return map;
    }


    @Override
    public List<WarehouseOutput> findAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream().map(warehouse -> conversionService.convert(warehouse, WarehouseOutput.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Warehouse> findAllWarehousesEntities() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses;
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
    public Warehouse findWarehouseEntityByName(String title){
        Warehouse warehouse = warehouseRepository.findByName(title).orElseThrow(
                () -> new EntityNotFoundException("Warehouse", "name", title));

        return warehouse;
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
    public void changeNameOfWarehouse(int id, String name, String email) {
        User user = userService.findUserEntityByEmail(email);
        if(user.getRole().equals(Role.OWNER) || user.getRole().equals(Role.MANAGER)){
            Warehouse warehouse = warehouseRepository.findWarehouseEntityByWarehouseId(id);
            if(name.length() > 50){
                throw new InvalidDataException("Name can not be more than 50 symbols");
            }

            warehouse.setName(name);

            warehouseRepository.save(warehouse);
        } else {
            throw new UnauthorizedOperationException("User is not owner or manager!");
        }
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

                warehousePartService.createWarehousePart(warehouse, part, quantityOfPart);
            }
        }

        if (warehousePartOutputs.isEmpty()){
            Warehouse warehouse = warehouseRepository.findWarehouseEntityByName(warehouseName);
            Part part = partService.getPartEntity(partTitle);

            warehousePartService.createWarehousePart(warehouse, part, quantityOfPart);

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
