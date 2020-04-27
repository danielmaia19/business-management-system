package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.ProjectNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectNoteRepository extends CrudRepository<ProjectNote, Long> {
    List<ProjectNote> findAllByProject(Project project);
    List<ProjectNote> findAllByProjectOrderBySubmittedDateDesc(Project project);
    ProjectNote findNoteById(Long id);
}

