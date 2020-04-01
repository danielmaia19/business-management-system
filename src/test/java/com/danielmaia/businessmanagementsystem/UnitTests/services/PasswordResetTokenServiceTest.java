package com.danielmaia.businessmanagementsystem.UnitTests.services;

import com.danielmaia.businessmanagementsystem.Model.PasswordResetToken;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.PasswordResetTokenRepository;
import com.danielmaia.businessmanagementsystem.Service.PasswordResetTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordResetTokenServiceTest {

    @Mock
    private PasswordResetTokenRepository repository;

    @InjectMocks
    private PasswordResetTokenService service;

    private PasswordResetToken passwordResetToken;
    private List<PasswordResetToken> passwordResetTokens = new ArrayList<>();
    private User user;

    @BeforeEach
    void setUp() {

        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");
        passwordResetToken = new PasswordResetToken(UUID.randomUUID().toString(), user);

        passwordResetTokens.add(new PasswordResetToken(UUID.randomUUID().toString(), user));
        passwordResetTokens.add(new PasswordResetToken(UUID.randomUUID().toString(), user));
    }

    @Test
    public void testFindByToken() {
        when(repository.findByToken(passwordResetToken.getToken())).thenReturn(passwordResetToken);

        PasswordResetToken foundPasswordToken = service.findByToken(passwordResetToken.getToken());

        assertThat(foundPasswordToken).isNotNull();
        assertThat(foundPasswordToken).isEqualTo(passwordResetToken);
    }

    @Test
    public void testDeleteAllPasswordTokens() {
        service.deleteAllPasswordTokens(passwordResetTokens);
        verify(repository).deleteAll(passwordResetTokens);
    }

    @Test
    public void testDeleteToken() {
        service.deleteToken(passwordResetToken);
        verify(repository).delete(any(PasswordResetToken.class));
    }

    @Test
    public void testSaveToken() {
        service.saveToken(passwordResetToken);
        verify(repository).save(any(PasswordResetToken.class));
    }

}
