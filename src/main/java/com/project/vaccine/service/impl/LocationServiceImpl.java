package com.project.vaccine.service.impl;

import com.project.vaccine.entity.Location;
import com.project.vaccine.repository.LocationRepository;
import com.project.vaccine.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    /**
     *  lấy danh sách location
     *  */
    @Override
    public List<Location> findAllLocation() {
        return locationRepository.findAllLocation();
    }
}
