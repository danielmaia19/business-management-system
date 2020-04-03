package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientFile;
import com.danielmaia.businessmanagementsystem.Repository.ClientFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.StringUtils;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ClientFileService {

    @Autowired
    private ClientFileRepository clientFileRepository;

    public ClientFile saveFile(Client client, MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if (filename.contains("..")) {
            throw new RuntimeException("Could not upload file to database");
        }

        ClientFile dbClientFile = new ClientFile();
        dbClientFile.setFilename(StringUtils.cleanPath(file.getOriginalFilename()));
        dbClientFile.setFileType(file.getContentType());
        dbClientFile.setData(file.getBytes());
        dbClientFile.setClient(client);
        return clientFileRepository.save(dbClientFile);
    }

    public ClientFile getFile(String fileId) {
        return clientFileRepository.findByClientFileId(fileId);
    }

    public List<ClientFile> findAllByClient(Client client) {
        return clientFileRepository.findAllByClient(client);
    }

    public void deleteFile(String id){
        clientFileRepository.deleteByClientFileId(id);
    }

}
