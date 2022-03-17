package com.project.vaccine.service.impl;

import com.project.vaccine.entity.Position;
import com.project.vaccine.repository.PositionRepository;
import com.project.vaccine.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepository positionRepository;
    @Override
    public List<Position> getAllPosition() {
        return positionRepository.getAllPosition();
    }
}
