package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByName(String clientName);
}
