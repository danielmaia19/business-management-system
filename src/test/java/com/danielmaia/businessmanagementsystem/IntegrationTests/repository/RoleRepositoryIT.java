package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("Role Repository - Integration Test")
public class RoleRepositoryIT {

    @Autowired
    private RoleRepository repository;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role("ROLE_USER");
    }

    @Test
    @DisplayName("Find Role By Name")
    public void testFindByName() {
        repository.save(role);
        Role foundRole = repository.findByName("ROLE_USER");
        assertThat(foundRole.getName()).isNotNull();
        assertThat(foundRole.getName()).isEqualTo("ROLE_USER");
    }

}
