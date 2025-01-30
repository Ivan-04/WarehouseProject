package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.exceptions.InsufficientPartException;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.WarehousePart;
import com.example.warehouseproject.models.dtos.WarehousePartOutput;
import com.example.warehouseproject.repositories.WarehousePartsRepository;
import com.example.warehouseproject.services.contracts.PartService;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehousePartServiceImpl implements WarehousePartService {

    private final WarehousePartsRepository warehousePartsRepository;
    private final ConversionService conversionService;
    private final PartService partService;


    @Override
    public List<WarehousePartOutput> findAllWarehousesParts() {
        List<WarehousePart> warehousesParts = warehousePartsRepository.findAll();
        return warehousesParts.stream().map(warehouseParts -> conversionService.convert(warehouseParts, WarehousePartOutput.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WarehousePart> getWarehousePartsWithFilters(String title) {

        List<WarehousePart> warehousePartList = warehousePartsRepository.findAll();
        List<WarehousePart> warehousePartsToReturn = new ArrayList<>();

        for (WarehousePart warehousePart : warehousePartList) {
            if (warehousePart.getPart().getTitle().toLowerCase().contains(title.toLowerCase())) {
                warehousePartsToReturn.add(warehousePart);
            }
        }

        return warehousePartsToReturn;
    }

    @Override
    public WarehousePart findWarehousePart(Warehouse warehouse, Part part) {

        return warehousePartsRepository.findByWarehouseAndPart(warehouse, part);
    }

    @Override
    public void createWarehousePart(Warehouse warehouse, Part part, int quantityOfPart) {

        WarehousePart warehousePart = WarehousePart.builder()
                .warehouse(warehouse)
                .part(part)
                .quantity(quantityOfPart)
                .build();

        warehousePartsRepository.save(warehousePart);
    }

    @Override
    public void changeTheQuantity(WarehousePart warehousePart, int quantityOfPart) {
        warehousePart.setQuantity(warehousePart.getQuantity() + quantityOfPart);
        warehousePartsRepository.save(warehousePart);
    }

    @Override
    public void removePartOfThisType(WarehousePart warehousePart, int quantityOfPart) {

        if (warehousePart.getQuantity() < quantityOfPart) {
            throw new InsufficientPartException("Not enough parts of this type!");
        }

        warehousePart.setQuantity(warehousePart.getQuantity() - quantityOfPart);
        warehousePartsRepository.save(warehousePart);
    }

    @Override
    public void deletePartOfThisType(WarehousePart warehousePart) {
        warehousePartsRepository.delete(warehousePart);
    }

}
