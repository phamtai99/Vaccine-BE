package com.project.vaccine.repository;

import com.project.vaccine.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VaccineTypeRepository extends JpaRepository<VaccineType,Integer> {

    /**
     *
     * create vaccineType
     * @return
     */
    @Modifying
    @Query(value = "insert into vaccine_type(name) values (?1);", nativeQuery = true)
    void createVaccineType(String name);

    /**
     *
     * find vaccineType by name
     * @return
     */
    @Query(value = "select * from vaccine_type where name = ?;",nativeQuery = true)
    VaccineType findName(String name);

    @Transactional
    @Modifying
    @Query(value = "update vaccine_type   set name = ?1  where vaccine_type_id = ?2", nativeQuery = true)
    void editVaccineType( String name,int id);

}