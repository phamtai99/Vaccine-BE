package com.project.vaccine.service;

import com.project.vaccine.entity.ImportAndExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImportAndExportService {

    Page<ImportAndExport> findAll(String action, Pageable pageable);

    Page<ImportAndExport> search(String action, String vaccineType, String origin, Pageable pageable);

    ImportAndExport findById(Integer id);

    void editPrice(Integer id, Long price);


}
