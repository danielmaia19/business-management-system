package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("User Repository - Integration Test")
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Find User By Username")
    public void testFindByUsername() {
        User user = new User("Daniel", "Maia", "dmaia", "arsenal", "dmaia@gmail.com");
        userRepository.save(user);
        User foundUser = userRepository.findByUsername("dmaia");
        assertThat("dmaia").isEqualTo(foundUser.getUsername());
    }

    @Test
    @DisplayName("Find User By Email")
    public void testFindByEmail() {
        User user = new User("Daniel", "Maia", "dmaia", "arsenal", "dmaia@gmail.com");
        userRepository.save(user);
        User userFound = userRepository.findByEmail("dmaia@gmail.com");
        assertThat("dmaia@gmail.com").isEqualTo(userFound.getEmail());
    }

}
