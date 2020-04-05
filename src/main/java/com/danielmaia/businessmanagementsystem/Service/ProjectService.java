package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void saveProject(Project projectName) { projectRepository.save(projectName); }

    public List<Project> findAllByClient(Client client) { return projectRepository.findAllByClient(client); }

    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }

    public int countProjectsByClient(Client client) {
        return projectRepository.countProjectsByClient(client);
    }

    public int countProjectsByStatusEqualsOrStatusEquals(String todo, String inprogress) {
        return projectRepository.countProjectsByStatusEqualsOrStatusEquals(todo, inprogress);
    }

    public void deleteProjectByName(String name) {
        projectRepository.deleteProjectByName(name);
    }

}
