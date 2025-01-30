package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.dtos.PartInput;
import com.example.warehouseproject.models.dtos.PartOutput;
import com.example.warehouseproject.models.dtos.PartOutputId;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PartService {
    List<PartOutput> findAllParts();

    Page<Part> getPartsWithFilters(String title, String description, int page, int size, String sortBy, String sortDirection);

    PartOutput findPartById(int id);

    Part findPartEntityById(int id);

    PartOutput findPartByTitle(String title);

    Part findPartEntityByTitle(String title);

    PartOutputId createPart(PartInput partInput);

    Part getPartEntity(String title);
}
