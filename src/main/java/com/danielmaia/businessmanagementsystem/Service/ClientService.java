package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client findByName(String clientName) {
        return clientRepository.findByName(clientName);
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

}
