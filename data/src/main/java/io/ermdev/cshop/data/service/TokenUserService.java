package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.repository.TokenRepository;
import io.ermdev.cshop.data.repository.TokenUserRepository;
import io.ermdev.cshop.data.repository.UserRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenUserService {

    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private TokenUserRepository tokenUserRepository;

    @Autowired
    public TokenUserService(TokenRepository tokenRepository, UserRepository userRepository,
                            TokenUserRepository tokenUserRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.tokenUserRepository = tokenUserRepository;
    }

    public User findUserByTokenId(Long tokenId) {
        return tokenUserRepository.findUserByTokenId(tokenId);
    }

    public User addUserToToken(Long tokenId, Long userId) throws EntityException {
        final Token token = tokenRepository.findById(tokenId);
        final User user = userRepository.findById(userId);
        if(token == null) {
            throw new EntityException("No token found");
        }
        if(user == null) {
            throw new EntityException("No user found");
        }
        if(findUserByTokenId(tokenId) != null) {
            throw new EntityException("An user already exists");
        }
        tokenUserRepository.addUserToToken(tokenId, userId);
        return user;
    }

    public User removeUserFromToken(Long tokenId, Long userId) throws EntityException {
        final Token token = tokenRepository.findById(tokenId);
        final User user = findUserByTokenId(tokenId);
        if(token == null) {
            throw new EntityException("No token found");
        }
        if(user == null) {
            throw new EntityException("No user to remove");
        }
        tokenUserRepository.removeUserFromToken(tokenId);
        return user;
    }
}
