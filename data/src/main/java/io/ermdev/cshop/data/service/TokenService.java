package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.repository.TokenRepository;
import io.ermdev.cshop.data.repository.TokenUserRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private TokenRepository tokenRepository;
    private TokenUserRepository tokenUserRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository, TokenUserRepository tokenUserRepository) {
        this.tokenRepository = tokenRepository;
        this.tokenUserRepository = tokenUserRepository;
    }

    public Token findById(Long tokenId) throws EntityException {
        final Token token = tokenRepository.findById(tokenId);
        if (token != null) {
            final User user = tokenUserRepository.findUserByTokenId(tokenId);
            token.setUser(user);
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token findByKey(String key) throws EntityException {
        final Token token = tokenRepository.findByKey(key);
        if (token != null) {
            final User user = tokenUserRepository.findUserByTokenId(token.getId());
            token.setUser(user);
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    public List<Token> findAll() throws EntityException {
        final List<Token> tokens = tokenRepository.findAll();
        if (tokens != null) {
            tokens.parallelStream().forEach(token -> {
                final User user = tokenUserRepository.findUserByTokenId(token.getId());
                token.setUser(user);
            });
            return tokens;
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token save(Token token) throws EntityException {
        if (token != null) {
            if (token.getId() == null) {
                if (token.getKey() == null || token.getKey().trim().isEmpty()) {
                    throw new EntityException("Key is required");
                }
                if (token.getExpiryDate() == null || token.getExpiryDate().toString().trim().isEmpty()) {
                    throw new EntityException("ExpiryDate is required");
                }
                final Long generatedId = IdGenerator.randomUUID();
                token.setId(generatedId);
                tokenRepository.add(token);
                return token;
            } else {
                final Token o = tokenRepository.findById(token.getId());
                if (o != null) {
                    if (token.getKey() == null && token.getKey().trim().isEmpty()) {
                        token.setKey(o.getKey());
                    }
                    if (token.getExpiryDate() == null && token.getExpiryDate().toString().trim().isEmpty()) {
                        token.setExpiryDate(o.getExpiryDate());
                    }
                    tokenRepository.update(token);
                    return token;
                } else {
                    token.setId(null);
                    return save(token);
                }
            }
        } else {
            throw new NullPointerException("Token is null");
        }
    }

    public Token delete(Long tokenId) throws EntityException {
        final Token token = tokenRepository.findById(tokenId);
        if (token != null) {
            User user = tokenUserRepository.findUserByTokenId(token.getId());
            token.setUser(user);
            tokenRepository.delete(token);
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token delete(Token token) throws EntityException {
        if (token != null) {
            final Token o = tokenRepository.findById(token.getId());
            if (o != null) {
                User user = tokenUserRepository.findUserByTokenId(token.getId());
                token.setUser(user);
                tokenRepository.delete(o);
                return o;
            } else {
                throw new EntityException("No token found");
            }
        } else {
            throw new NullPointerException("Token is null");
        }
    }
}
