package com.project.vaccine.service;

import com.project.vaccine.entity.Storage;

import java.util.List;

public interface StorageService {


   List<Storage> findAll();

   Storage findById(Integer id);

   /**
    * create Storage
    * @return
    */
   void createStorage(int quantity , int vaccineId);

   void editStorage(int quantity, int vaccineId);

   /**
    * @param id
    * @return
    */
   Storage getStorage(int id);
   /**

    * @param storage
    */
   void saveStorage(Storage storage);
}
