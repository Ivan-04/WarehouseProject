package com.example.warehouseproject.converters;

import com.example.warehouseproject.models.Line;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.LineOutput;
import com.example.warehouseproject.models.dtos.UserOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LineToLineOutput implements Converter<Line, LineOutput> {

    @Override
    public LineOutput convert(Line line) {
        return LineOutput.builder()
                .num(line.getNum())
                .description(line.getDescription())
                .date(line.getCreatedAt())
                .build();
    }
}
