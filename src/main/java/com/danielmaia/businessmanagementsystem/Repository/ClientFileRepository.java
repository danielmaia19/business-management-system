package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientFileRepository extends CrudRepository<ClientFile, String> {
    ClientFile findByClientFileId(String id);
    List<ClientFile> findAllByClient(Client client);
    void deleteByClientFileId(String id);
}
