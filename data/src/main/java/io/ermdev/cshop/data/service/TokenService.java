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
            return null;
        }
    }

//    public Token delete(Long tokenId) throws EntityException {
//        final TokenDto tokenDto = tokenRepository.findById(tokenId);
//        if (tokenDto != null) {
//            tokenRepository.delete(tokenDto);
//            return modelMapper.set(tokenDto)
//                    .field("userId", "user")
//                    .convertFieldToType("user", User.class)
//                    .getTransaction().mapTo(Token.class);
//        } else {
//            throw new EntityException("No token found");
//        }
//    }
//
//    public Token delete(Token token) throws EntityException {
//        final TokenDto tokenDto = tokenRepository.findById(token.getId());
//        if (tokenDto != null) {
//            tokenRepository.delete(tokenDto);
//            return modelMapper.set(tokenDto)
//                    .field("userId", "user")
//                    .convertFieldToType("user", User.class)
//                    .getTransaction().mapTo(Token.class);
//        } else {
//            throw new EntityException("No token found");
//        }
//    }
}
