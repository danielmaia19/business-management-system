package com.danielmaia.businessmanagementsystem.IntegrationTests.services;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");
    }

    @Test
    public void testFindByEmail() {
        //When
        when(repository.findByEmail("dmaia@gmail.com")).thenReturn(user);
        User foundUserByEmail = service.findByEmail("dmaia@gmail.com");

        //Then
        assertThat(foundUserByEmail).isNotNull();
        assertThat(foundUserByEmail).isEqualTo(user);

        verify(repository, atMostOnce()).findByEmail(anyString());
    }

    @Test
    public void testFindByUsername() {
        //When
        when(repository.findByUsername("dmaia")).thenReturn(user);
        User foundUserByUsername = service.findByUsername("dmaia");

        // Then
        assertThat(foundUserByUsername).isNotNull();
        assertThat(foundUserByUsername).isEqualTo(user);

        verify(repository, atMostOnce()).findByUsername(anyString());

    }

    @Test
    void testSaveUser() {
        service.saveUser(user);
        verify(repository).save(any(User.class));
    }
}
