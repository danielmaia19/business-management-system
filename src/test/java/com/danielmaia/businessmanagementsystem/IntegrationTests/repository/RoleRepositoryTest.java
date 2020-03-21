package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DisplayName("Role Repository - Integration Test")
public class RoleRepositoryTest {

    @Mock
    private RoleRepository repository;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role("ROLE_USER");
    }

    @Test
    public void testFindByName() {
        when(repository.findByName("ROLE_USER")).thenReturn(role);
        Role roleFound = repository.findByName("ROLE_USER");

        assertThat(roleFound).isNotNull();
        assertThat(roleFound).isEqualTo(role);

        verify(repository, atMostOnce()).findByName(anyString());
    }

}
