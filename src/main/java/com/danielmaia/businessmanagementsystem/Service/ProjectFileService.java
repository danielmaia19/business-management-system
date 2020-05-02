package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.ProjectFile;
import com.danielmaia.businessmanagementsystem.Repository.ProjectFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class ProjectFileService {

    @Autowired
    private ProjectFileRepository projectFileRepository;

    public ProjectFile saveFile(Project project, MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if (filename.contains("..")) {
            throw new RuntimeException("Could not upload file to database");
        }

        ProjectFile dbProjectFile = new ProjectFile();
        dbProjectFile.setFilename(StringUtils.cleanPath(file.getOriginalFilename()));
        dbProjectFile.setFileType(file.getContentType());
        dbProjectFile.setData(file.getBytes());
        dbProjectFile.setProject(project);
        return projectFileRepository.save(dbProjectFile);
    }

    public ProjectFile getFile(String fileId) {
        return projectFileRepository.findByProjectFileId(fileId);
    }

    public List<ProjectFile> findAllByProject(Project project) {
        return projectFileRepository.findAllByProject(project);
    }

    public void deleteFile(String id){
        projectFileRepository.deleteByProjectFileId(id);
    }

}
