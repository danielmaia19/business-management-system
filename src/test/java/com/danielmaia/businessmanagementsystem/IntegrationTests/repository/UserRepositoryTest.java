package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DisplayName("User Repository - Integration Test")
public class UserRepositoryTest {

    @Mock
    private UserRepository repository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");
    }

    @Test
    public void testFindByUsername() {
        when(repository.findByUsername("dmaia")).thenReturn(user);
        User foundUser = repository.findByUsername("dmaia");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser).isEqualTo(user);

        verify(repository).findByUsername(anyString());
    }

    @Test
    public void testFindByEmail() {
        when(repository.findByEmail("dmaia@gmail.com")).thenReturn(user);
        User userFound = repository.findByEmail("dmaia@gmail.com");

        assertThat(userFound).isNotNull();
        assertThat(userFound).isEqualTo(user);

        verify(repository).findByEmail(anyString());
    }
}
