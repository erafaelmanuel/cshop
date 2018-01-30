package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.dto.TokenDto;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.repository.TokenRepository;
import io.ermdev.cshop.data.repository.UserRepository;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.mapfierj.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TokenService(TokenRepository tokenRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Token findById(Long tokenId) throws EntityException {
        final Token token = tokenRepository.findById(tokenId);
        if (token != null) {
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token findByKey(String key) throws EntityException {
        final Token token = tokenRepository.findByKey(key);
        if (token != null) {
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    public List<Token> findAll() throws EntityException {
        final List<Token> tokens = tokenRepository.findAll();
        if (tokens != null) {
            return tokens;
        } else {
            throw new EntityException("No token found");
        }
    }
}
