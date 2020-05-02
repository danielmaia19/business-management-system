package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Client> findAllByUser(User user) {
        return clientRepository.findAllByUser(user);
    }

    public void saveAllClients(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

    public int countAllByUser(User user) {
        return clientRepository.countAllByUser(user);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public boolean existsByName(String name) {
        return clientRepository.existsByName(name);
    }

    public void saveImage(String clientName, MultipartFile imageFile) throws Exception {

        Files.createDirectories(Paths.get("src/main/resources/static/logos/" + clientName));

        String folder = "src/main/resources/static/logos/" + clientName + "/";
        byte[] bytes = imageFile.getBytes();
        String extension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
        String filename = "logo."+extension;
        Path paths = Paths.get(folder + filename);

        Files.write(paths, bytes);

    }
}
