package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DisplayName("Project Repository - Integration Test")
public class ProjectRepositoryTest {

    @Mock
    private ProjectRepository repository;

    private User user;
    private List<Project> projects;

    @BeforeEach
    void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        projects = new ArrayList<>();
        projects.add(new Project("Project 1"));
        projects.add(new Project("Project 2"));

    }


    @Test
    public void testFindProjectsByUser() {
        when(repository.findProjectsByUser(user)).thenReturn(projects);
        List<Project> projectsFoundByUser = repository.findProjectsByUser(user);

        assertThat(projectsFoundByUser).isNotNull();
        assertThat(projectsFoundByUser).containsAll(projects);

        verify(repository, atMostOnce()).findProjectsByUser(user);
    }
}
