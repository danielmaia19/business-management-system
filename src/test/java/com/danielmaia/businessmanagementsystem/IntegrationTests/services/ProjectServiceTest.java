package com.danielmaia.businessmanagementsystem.IntegrationTests.services;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import com.danielmaia.businessmanagementsystem.Service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    private Project project;
    private User user;
    private List<Project> projects;

    @BeforeEach
    public void setup(){

        project = new Project("test");

        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        projects = new ArrayList<>();
        projects.add(new Project("Test 1"));
        projects.add(new Project("Test 2"));

    }

    @Test
    public void testFindProjectByUser() {
         // When
        when(repository.findProjectsByUser(user)).thenReturn(projects);

        List<Project> foundProjects = service.findProjectsByUser(user);

        assertThat(foundProjects).isNotNull();
        assertThat(foundProjects).isEqualTo(projects);

        verify(repository).findProjectsByUser(any(User.class));
    }

    @Test
    public void testSaveProject() {
        service.saveProject(project);
        verify(repository).save(any(Project.class));
    }


}
