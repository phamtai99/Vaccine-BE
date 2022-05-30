package com.project.vaccine.repository;

import com.project.vaccine.entity.Storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface StorageRepository extends JpaRepository<Storage,Integer> {
    /**
     *
     * create Storage
     * @return
     */
    @Modifying
    @Query(value = "insert into storage(quantity, vaccine_id) values(?1, ?2);", nativeQuery = true)
    void createStorage(int quantity, int vaccineId);

    /**
     *
     * @param id
     * @return
     */
    @Query(value = "select  * from storage where vaccine_id = ?",nativeQuery = true)
    Storage getStorage(int id);

    /**
     *
     * Get quantity of a vaccine
     */
    Storage findAllByVaccine_VaccineIdIs(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update storage   set quantity = ?1  where vaccine_id = ?2", nativeQuery = true)
    void editStorage( int quantity,int id);

}
