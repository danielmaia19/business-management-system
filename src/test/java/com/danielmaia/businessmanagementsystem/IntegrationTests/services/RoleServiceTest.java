package com.danielmaia.businessmanagementsystem.IntegrationTests.services;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import com.danielmaia.businessmanagementsystem.Service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;
    private List<Role> roles;


    @BeforeEach
    void setUp() {
        role = new Role("ROLE_ADMIN");

        roles = new ArrayList<>();
        roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));
    }

    @Test
    @DisplayName("Find By Name - Role Service")
    public void testFindByName() {
        // When
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(role);

        // testing this as if it was a real situation
        Role foundRole = roleService.findByName("ROLE_ADMIN");

        // Then
        assertThat(foundRole).isNotNull();
        assertThat(foundRole).isEqualTo(role);

        verify(roleRepository, atMostOnce()).findByName("admin");
    }

    @Test
    @DisplayName("Find All - Role Service")
    public void testFindAll() {
        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> foundAllRoles = roleService.findAll();

        assertThat(foundAllRoles).isNotNull();
        assertThat(foundAllRoles).containsAll(roles);
    }

}
