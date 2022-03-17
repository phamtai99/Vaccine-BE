package com.project.vaccine.service.impl;

import com.project.vaccine.entity.Provider;
import com.project.vaccine.repository.ProviderRepository;
import com.project.vaccine.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    ProviderRepository providerRepository;


    /**
     * create Provider
     * @return
     */
    @Override
    public void createProvider(String name) {
        providerRepository.createProvider(name);
    }


    /**

     * find by name Provider
     * @return
     */
    @Override
    public Provider searchNameProvider(String name) {
        return providerRepository.searchNameProvider(name);
    }
}
