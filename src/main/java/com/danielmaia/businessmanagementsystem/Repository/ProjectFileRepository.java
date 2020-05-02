package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.ProjectFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectFileRepository extends CrudRepository<ProjectFile, String> {
    ProjectFile findByProjectFileId(String id);
    List<ProjectFile> findAllByProject(Project project);
    void deleteByProjectFileId(String id);
}
