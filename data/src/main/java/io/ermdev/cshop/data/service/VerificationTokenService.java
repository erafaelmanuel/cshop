package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.mapper.UserRepository;
import io.ermdev.cshop.data.mapper.VerificationTokenRepository;
import io.ermdev.cshop.model.entity.User;
import io.ermdev.cshop.model.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;
    private UserRepository userRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    public VerificationToken findByToken(String token) throws EntityNotFoundException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null)
            throw new EntityNotFoundException("No VerificationToken found with token:" + token);
        User user = userRepository.findById(verificationToken.getUserId());
        verificationToken.setUser(user);

        return verificationToken;
    }

    public VerificationToken findByUserId(Long userId) throws EntityNotFoundException {
        VerificationToken verificationToken = verificationTokenRepository.findByUserId(userId);
        if(verificationToken == null)
            throw new EntityNotFoundException("No VerificationToken found with userId:" + userId);
        return verificationToken;
    }

    public VerificationToken add(VerificationToken verificationToken) {
        verificationTokenRepository.add(verificationToken.getToken(), verificationToken.getExpiryDate(),
                verificationToken.getUserId());
        return verificationToken;
    }

    public VerificationToken updateById(Long verificationId, VerificationToken verificationToken) {
        return null;
    }

    public VerificationToken deleteById(Long verificationTokenId) {
        verificationTokenRepository.deleteById(verificationTokenId);
        return null;
    }


}
