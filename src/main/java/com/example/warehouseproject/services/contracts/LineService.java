package com.example.warehouseproject.services.contracts;

import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.LineInput;
import com.example.warehouseproject.models.dtos.LineOutput;
import com.example.warehouseproject.models.dtos.LineOutputId;

import java.util.List;
import java.util.Optional;

public interface LineService {

    List<LineOutput> findAllLines();

    LineOutput findLineById(int id);

    LineOutput findLineByNum(int num);

    LineOutputId createLine(LineInput lineInput, User user);
}
