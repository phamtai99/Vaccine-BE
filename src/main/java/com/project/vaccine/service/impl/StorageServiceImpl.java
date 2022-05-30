package com.project.vaccine.service.impl;

import com.project.vaccine.controller.SecurityController;
import com.project.vaccine.entity.Storage;
import com.project.vaccine.repository.StorageRepository;
import com.project.vaccine.service.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    private static Logger logger= LogManager.getLogger(StorageServiceImpl.class);
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

    @Override
    public void editStorage(int quantity, int vaccineId) {
        try{
             storageRepository.editStorage(quantity, vaccineId);
        }catch (Exception ex){
            logger.error("Lỗi chỉnh sửa số lượng còn lại của vaccine");
        }
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
        try {
            storageRepository.save(storage);
        }catch (Exception ex){
            logger.error("Loỗi update "+ ex);
        }


    }
}
