package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.dto.TokenDto;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.repository.TokenRepository;
import io.ermdev.cshop.data.repository.UserRepository;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private SimpleMapper mapper;

    @Autowired
    public TokenService(TokenRepository tokenRepository, UserRepository userRepository, SimpleMapper mapper) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    Token findById(Long tokenId) throws EntityException {
        final TokenDto tokenDto = tokenRepository.findById(tokenId);
        if(tokenDto != null) {
            User user = userRepository.findById(tokenDto.getUserId());
            Token token = mapper.set(tokenDto).mapTo(Token.class);
            token.setUser(user);
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    Token findByKey(String key) throws EntityException {
        final TokenDto tokenDto = tokenRepository.findByKey(key);
        if(tokenDto != null) {
            User user = userRepository.findById(tokenDto.getUserId());
            Token token = mapper.set(tokenDto).mapTo(Token.class);
            token.setUser(user);
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    Token findByUserId(Long userId) throws EntityException {
        final TokenDto tokenDto = tokenRepository.findByUserId(userId);
        if(tokenDto != null) {
            User user = userRepository.findById(userId);
            Token token = mapper.set(tokenDto).mapTo(Token.class);
            token.setUser(user);
            return token;
        } else {
            throw new EntityException("No token found");
        }
    }

    /**TODO**/
    Token save(Token token) {
        return null;
    }
}
