package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
@DisplayName("Project Repository - Integration Test")
public class ProjectRepositoryIT {

    @Autowired
    private ProjectRepository repository;

    private User user;
    private List<Project> projects = new ArrayList<>();

    @BeforeEach
    void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password123456", "dmaia@gmail.com");

        projects.add(new Project("Project 1", user));
        projects.add(new Project("Project 2", user));
    }

    @Test
    @Disabled
    public void testFindProjectsByUser() {
        repository.saveAll(projects);

        List<Project> foundProjects = repository.findProjectsByUser(user);

        assertThat(foundProjects).isNotNull();
        assertThat(foundProjects).isEqualTo(projects);
    }
}
