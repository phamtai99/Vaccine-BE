package com.project.vaccine.service;

import com.project.vaccine.entity.Provider;

public interface ProviderService {

    /**
     * create Provider
     * @return
     */
    void createProvider(String name);

    /**
     * find by name Provider
     * @return
     */
    Provider searchNameProvider(String name);
}

