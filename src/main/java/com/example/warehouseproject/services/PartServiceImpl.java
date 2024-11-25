package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.repositories.PartRepository;
import com.example.warehouseproject.services.contracts.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ConversionService conversionService;

    @Override
    public List<PartOutput> findAllParts() {
        List<Part> parts = partRepository.findAll();
        return parts.stream().map(part -> conversionService.convert(part, PartOutput.class)).collect(Collectors.toList());

    }

    @Override
    public PartOutput findPartById(int id){
        Part part = partRepository.findPartByPartId(id).orElseThrow(
                () -> new EntityNotFoundException("Part", id));

        return conversionService.convert(part, PartOutput.class);
    }

    @Override
    public PartOutput findPartByTitle(String title){
        Part part = partRepository.findPartByTitle(title).orElseThrow(
                () -> new EntityNotFoundException("Part", "title",title));

        return conversionService.convert(part, PartOutput.class);
    }

    @Override
    public PartOutputId createPart(PartInput partInput) {

        Part part = Part.builder()
                .title(partInput.getTitle())
                .description(partInput.getDescription())
                .price(partInput.getPrice())
                .createdAt(LocalDateTime.now())
                .build();

        Part existingPart = partRepository.findPartEntityByTitle(partInput.getTitle());

        if (existingPart != null) {
            throw new DuplicateEntityException("Part", "title", partInput.getTitle());
        }

        partRepository.save(part);

        return conversionService.convert(part, PartOutputId.class);
    }

}
