package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.ClientFile;
import com.danielmaia.businessmanagementsystem.Service.ClientFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ClientFileDownloadController {

    @Autowired
    private ClientFileService clientFileService;

    @GetMapping("/clients/{name}/downloads/{clientFileId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name, @PathVariable String clientFileId, HttpServletRequest httpServletRequest){

        ClientFile clientFile = clientFileService.getFile(clientFileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(clientFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + clientFile.getFilename() + "\"").body(new ByteArrayResource(clientFile.getData()));
    }

}
