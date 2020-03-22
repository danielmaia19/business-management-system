package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
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
@DisplayName("User Repository - Integration Test")
public class UserRepositoryIT {

    @Autowired
    private UserRepository repository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");
    }

    @Test
    public void testFindByUsername() {
        repository.save(user);
        repository.findByUsername("dmaia");
        User foundUser = repository.findByUsername("dmaia");
        assertThat(foundUser).isNotNull();
        assertThat("dmaia").isEqualTo(foundUser.getUsername());
    }

    @Test
    public void testFindByEmail() {
        repository.save(user);
        repository.findByEmail("dmaia@gmail.com");
        User foundUser = repository.findByEmail("dmaia@gmail.com");
        assertThat(foundUser).isNotNull();
        assertThat("dmaia@gmail.com").isEqualTo(foundUser.getEmail());
    }
}
