package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.mapper.VerificationTokenRepository;
import io.ermdev.cshop.model.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public VerificationToken add(VerificationToken verificationToken) {
        verificationTokenRepository.add(verificationToken.getToken(), verificationToken.getExpiryDate(),
                verificationToken.getUser().getId());
        return verificationToken;
    }

    public VerificationToken deleteById(Long verificationTokenId) {
        verificationTokenRepository.deleteById(verificationTokenId);
        return null;
    }


}
