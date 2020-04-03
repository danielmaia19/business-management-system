package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.File;
import com.danielmaia.businessmanagementsystem.Service.ClientFileService;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;

@Transactional
@Controller
@Slf4j
public class ClientFileController {

    @Autowired
    private ClientFileService clientFileService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/{name}/upload")
    public String testingUpload(@PathVariable("name") String name, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        Client client = clientService.findByName(name);
        File fileName = clientFileService.saveFile(client, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/clients/" + name + "/downloads/")
                .path(fileName.getClientFileId())
                .toUriString();

        log.info(fileDownloadUri);

        redirectAttributes.addFlashAttribute("uploaded", "File was successfully uploaded");

        return "redirect:/clients/"+name;
    }


    @PostMapping("/clients/{name}/downloads/{clientFileId:.+}/delete")
    public String deleteFile(@PathVariable String name, @PathVariable String clientFileId, RedirectAttributes redirectAttributes) {

        clientFileService.deleteFile(clientFileId);

        redirectAttributes.addFlashAttribute("deleted", "File was successfully uploaded");

        return "redirect:/clients/"+name;
    }

    //@PostMapping("/multipleFiles")
    //public List<File> uploadingMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    //    return Arrays.asList(files).stream().map(file -> testingUpload("test", file)).collect(Collectors.toList());
    //}

}
