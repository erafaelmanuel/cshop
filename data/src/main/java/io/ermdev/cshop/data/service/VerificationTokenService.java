package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.repository.UserRepository;
import io.ermdev.cshop.data.repository.VerificationTokenRepository;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Deprecated
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
        User user = userRepository.findById(userId);
        verificationToken.setUser(user);
        return verificationToken;
    }

    public VerificationToken add(VerificationToken verificationToken) {
        verificationTokenRepository.add(verificationToken.getToken(), verificationToken.getExpiryDate(),
                verificationToken.getUserId());
        return verificationToken;
    }

    public VerificationToken updateById(Long verificationTokenId, VerificationToken verificationToken)
            throws EntityNotFoundException{
        final VerificationToken oldVerificationToken = verificationTokenRepository.findById(verificationTokenId);
        if (oldVerificationToken==null)
            throw new EntityNotFoundException("No verification token found with id:" + verificationTokenId);
        if(verificationToken.getToken()==null || verificationToken.getToken().equals(""))
            verificationToken.setToken(oldVerificationToken.getToken());
        if(verificationToken.getUserId()==null)
            verificationToken.setUserId(oldVerificationToken.getUserId());
        if(verificationToken.getExpiryDate()==null)
            verificationToken.setExpiryDate(oldVerificationToken.getExpiryDate());
        if(userRepository.findById(verificationToken.getUserId()) == null)
            throw new EntityNotFoundException("No user found with id:" + verificationToken.getUserId());

        verificationTokenRepository.updateById(verificationTokenId, verificationToken.getToken(),
                verificationToken.getUserId(), verificationToken.getExpiryDate());
        return verificationToken;
    }

    public VerificationToken updateByToken(String token, VerificationToken verificationToken) throws EntityNotFoundException{
        if(token==null)
            return verificationToken;

        final VerificationToken oldVerificationToken = verificationTokenRepository.findByToken(token);
        if (oldVerificationToken==null)
            throw new EntityNotFoundException("No verification token found with token:" + token);
        if(verificationToken.getToken()==null || verificationToken.getToken().equals(""))
            verificationToken.setToken(oldVerificationToken.getToken());
        if(verificationToken.getUserId()==null)
            verificationToken.setUserId(oldVerificationToken.getUserId());
        if(verificationToken.getExpiryDate()==null)
            verificationToken.setExpiryDate(oldVerificationToken.getExpiryDate());
        if(userRepository.findById(verificationToken.getUserId()) == null)
            throw new EntityNotFoundException("No user found with id:" + verificationToken.getUserId());

        verificationTokenRepository.updateByToken(token, verificationToken.getUserId(), verificationToken.getExpiryDate());
        return verificationToken;
    }

    public VerificationToken deleteById(Long verificationTokenId) {
        verificationTokenRepository.deleteById(verificationTokenId);
        return null;
    }

    //TODO
    public void deleteByUserId(Long userId) {
        verificationTokenRepository.deleteByUserId(userId);
    }
}
