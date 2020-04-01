package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.PasswordResetToken;
import com.danielmaia.businessmanagementsystem.Repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PasswordResetTokenService {

    private final static Logger LOGGER = Logger.getLogger(PasswordResetTokenService.class.getName());

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }


    public List<PasswordResetToken> findAllByExpiryDateBefore(Date expiryDate) {
        return passwordResetTokenRepository.findAllByExpiryDateBefore(expiryDate);
    }

    public void deleteAllPasswordTokens(List<PasswordResetToken> passwordResetTokens) {
        passwordResetTokenRepository.deleteAll(passwordResetTokens);
    }

    @Scheduled(cron = "0 0 1 * * MON")
    public void deleteAllOldPasswordTokens() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -2);
        Date daysAgo60 = cal.getTime();

        List<PasswordResetToken> allExpiredTokens = passwordResetTokenRepository.findAllByExpiryDateBefore(daysAgo60);
        LOGGER.log(Level.INFO, "Removal of 60 days or old tokens from the database. " + allExpiredTokens.size() + " were deleted from the database");
        passwordResetTokenRepository.deleteAll(allExpiredTokens);
    }

    public void deleteToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }

    public void saveToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }
}
