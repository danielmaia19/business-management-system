package com.danielmaia.businessmanagementsystem.UnitTests.services;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import com.danielmaia.businessmanagementsystem.Service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Project Service - Unit Test")
public class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    private Client client;
    private Project project;
    List<Project> projects = new ArrayList<>();

    @BeforeEach
    public void setup(){

        project = new Project("test");

        User user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        client = new Client("Client Name", user);

        projects.add(new Project("Test 1", client));
        projects.add(new Project("Test 2", client));
    }

    @Test
    @DisplayName("Find All by Client Test")
    public void testFindAllByClient() {
        // When
        when(repository.findAllByClient(client)).thenReturn(projects);

        List<Project> foundProjects = service.findAllByClient(client);

        assertThat(foundProjects).isNotNull();
        assertThat(foundProjects).isEqualTo(projects);

        verify(repository).findAllByClient(any(Client.class));
    }

    @Test
    @DisplayName("Save Project Test")
    public void testSaveProject() {
        service.saveProject(project);
        verify(repository).save(any(Project.class));
    }


}
