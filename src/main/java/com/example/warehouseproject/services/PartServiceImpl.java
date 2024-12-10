package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.Part;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.repositories.PartRepository;
import com.example.warehouseproject.services.contracts.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Part> getPartsWithFilters(String title, String description, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        String titleLike = title != null ? "%" + title + "%" : null;
        String descriptionLike = description != null ? "%" + description + "%" : null;
        return partRepository.findPartsByMultipleFields(titleLike, descriptionLike, pageable);
    }

    @Override
    public PartOutput findPartById(int id){
        Part part = partRepository.findPartByPartId(id).orElseThrow(
                () -> new EntityNotFoundException("Part", id));

        return conversionService.convert(part, PartOutput.class);
    }

    @Override
    public Part findPartEntityById(int id){

        return partRepository.findPartByPartId(id).orElseThrow(
                () -> new EntityNotFoundException("Part", id));
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

    @Override
    public Part getPartEntity(String title){
       return partRepository.findPartEntityByTitle(title);
    }

}
