package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
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

    public Page<Project> findAllUsersProjectsPages(List<Client> clients, Pageable pageable) {
        List<Project> projects = new ArrayList<>();

        for(Client client : clients) {
            projects.addAll(client.getProjects());
        }

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Project> list;

        if (projects.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, projects.size());
            list = projects.subList(startItem, toIndex);
        }

        Page<Project> projectPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), projects.size());

        return projectPage;
    }

    public Project findProjectById(int id) {
        return projectRepository.findByProjectId(id);
    }

    public void deleteProjectById(int id) {
        projectRepository.deleteByProjectId(id);
    }

}
