package com.project.vaccine.service.impl;

import com.project.vaccine.entity.Storage;
import com.project.vaccine.repository.StorageRepository;
import com.project.vaccine.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageRepository storageRepository;
    @Override
    public List<Storage> findAll() {
        return storageRepository.findAll();
    }

    @Override
    public Storage findById(Integer id) {
        return this.storageRepository.findById(id).orElse(null);
    }

    /**
     * create Storage
     * @return
     */
    @Override
    public void createStorage(int quantity, int vaccineId) {
        storageRepository.createStorage(quantity,vaccineId);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Storage getStorage(int id) {
        return storageRepository.getStorage(id);
    }

    /**
     * @param storage
     */
    @Override
    public void saveStorage(Storage storage) {
        storageRepository.save(storage);

    }
}
