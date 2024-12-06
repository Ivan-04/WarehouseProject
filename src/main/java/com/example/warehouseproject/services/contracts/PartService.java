package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.dtos.PartInput;
import com.example.warehouseproject.models.dtos.PartOutput;
import com.example.warehouseproject.models.dtos.PartOutputId;

import java.util.List;

public interface PartService {
    List<PartOutput> findAllParts();

    PartOutput findPartById(int id);

    Part findPartEntityById(int id);

    PartOutput findPartByTitle(String title);

    PartOutputId createPart(PartInput partInput);

    Part getPartEntity(String title);
}
