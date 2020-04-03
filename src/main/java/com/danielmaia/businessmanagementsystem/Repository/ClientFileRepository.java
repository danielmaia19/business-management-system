package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientFileRepository extends CrudRepository<File, String> {
    File findByClientFileId(String id);
    List<File> findAllByClient(Client client);
    void deleteByClientFileId(String id);
}
