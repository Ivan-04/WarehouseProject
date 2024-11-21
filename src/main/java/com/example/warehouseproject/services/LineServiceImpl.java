package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.*;
import com.example.warehouseproject.repositories.LineRepository;
import com.example.warehouseproject.services.contracts.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final ConversionService conversionService;

    @Override
    public List<LineOutput> findAllLines() {
        List<Line> lines = lineRepository.findAll();
        return lines.stream().map(line -> conversionService.convert(line, LineOutput.class)).collect(Collectors.toList());

    }

    @Override
    public LineOutput findLineById(int id){
        Line line = lineRepository.findLineByLineId(id).orElseThrow(
                () -> new EntityNotFoundException("Line", id));

        return conversionService.convert(line, LineOutput.class);
    }

    @Override
    public LineOutput findLineByNum(int num){
        String numToString = String.valueOf(num);

        Line line = lineRepository.findLineByNum(num).orElseThrow(
                () -> new EntityNotFoundException("Line", "num", numToString));

        return conversionService.convert(line, LineOutput.class);
    }

    @Override
    public LineOutputId createLine(LineInput lineInput, User user) {

        Line line = Line.builder()
                .num(lineInput.getNum())
                .description(lineInput.getDescription())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        Line existingLine = lineRepository.findLineEntityByNum(lineInput.getNum());

        if (existingLine != null) {
            throw new DuplicateEntityException("Line", "num", String.valueOf(lineInput.getNum()));
        }

        lineRepository.save(line);

        return conversionService.convert(line, LineOutputId.class);
    }

}
