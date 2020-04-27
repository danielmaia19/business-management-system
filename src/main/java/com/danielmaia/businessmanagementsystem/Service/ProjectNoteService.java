package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.ProjectNote;
import com.danielmaia.businessmanagementsystem.Repository.ProjectNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectNoteService {

    @Autowired
    private ProjectNoteRepository projectNoteRepository;

    public List<ProjectNote> findAllByClient(Project project) {
        return projectNoteRepository.findAllByProject(project);
    }

    public List<ProjectNote> findAllByProjectOrderBySubmittedDateDesc(Project project) {
        return projectNoteRepository.findAllByProjectOrderBySubmittedDateDesc(project);
    }

    public ProjectNote findNoteById(Long id) {
        return projectNoteRepository.findNoteById(id);
    }

    public void deleteNote(ProjectNote projectNote) {
        projectNoteRepository.delete(projectNote);
    }

    public void saveNote(ProjectNote projectNote) {
        projectNoteRepository.save(projectNote);
    }


}
