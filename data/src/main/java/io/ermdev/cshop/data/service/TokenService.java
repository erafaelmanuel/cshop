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

import java.util.ArrayList;
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
        final TokenDto tokenDto = tokenRepository.findById(tokenId);
        if (tokenDto != null) {
            return modelMapper.set(tokenDto)
                    .field("userId", "user")
                    .convertFieldToType("user", User.class)
                    .getTransaction().mapTo(Token.class);
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token findByKey(String key) throws EntityException {
        final TokenDto tokenDto = tokenRepository.findByKey(key);
        if (tokenDto != null) {
            return modelMapper.set(tokenDto)
                    .field("userId", "user")
                    .convertFieldToType("user", User.class)
                    .getTransaction().mapTo(Token.class);
        } else {
            throw new EntityException("No token found");
        }
    }

    public List<Token> findAll() throws EntityException {
        final List<TokenDto> tokenDtos = tokenRepository.findAll();
        if (tokenDtos != null) {
            List<Token> tokens = new ArrayList<>();
            tokenDtos.parallelStream().forEach(tokenDto -> {
                final Token token = modelMapper.set(tokenDto)
                        .field("userId", "user")
                        .convertFieldToType("user", User.class)
                        .getTransaction().mapTo(Token.class);
                tokens.add(token);
            });
            return tokens;
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token save(Token token) throws EntityException {
        if (token != null) {
            if (token.getId() == null) {
                final TokenDto tokenDto = modelMapper.set(token).getTransaction().mapTo(TokenDto.class);
                final Long generatedId = IdGenerator.randomUUID();
                if (token.getKey() == null || token.getKey().trim().isEmpty()) {
                    throw new EntityException("Key is required");
                }
                if (token.getExpiryDate() == null || token.getExpiryDate().toString().trim().isEmpty()) {
                    throw new EntityException("ExpiryDate is required");
                }
                if (token.getUser() == null || token.getUser().getId() != null) {
                    throw new EntityException("User is required");
                } else {
                    if (userRepository.findById(token.getUser().getId()) == null) {
                        throw new EntityException("No user found");
                    } else {
                        tokenDto.setUserId(token.getUser().getId());
                    }
                }
                tokenDto.setId(generatedId);
                tokenRepository.add(tokenDto);
                return token;
            } else {
                final TokenDto tokenDto = tokenRepository.findById(token.getId());
                if (tokenDto != null) {
                    if (token.getKey() != null && !token.getKey().trim().isEmpty()) {
                        tokenDto.setKey(token.getKey());
                    } else {
                        token.setKey(tokenDto.getKey());
                    }
                    if (token.getExpiryDate() != null && !token.getExpiryDate().toString().trim().isEmpty()) {
                        tokenDto.setExpiryDate(token.getExpiryDate());
                    } else {
                        token.setExpiryDate(tokenDto.getExpiryDate());
                    }
                    if (token.getUser() != null && token.getUser().getId() != null) {
                        final User user = userRepository.findById(token.getUser().getId());
                        if (user == null) {
                            throw new EntityException("No user found");
                        } else {
                            token.setUser(user);
                            tokenDto.setUserId(token.getUser().getId());
                        }
                    } else {
                        final User user = userRepository.findById(tokenDto.getUserId());
                        token.setUser(user);
                    }
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

    public Token delete(Long tokenId) throws EntityException {
        final TokenDto tokenDto = tokenRepository.findById(tokenId);
        if (tokenDto != null) {
            tokenRepository.delete(tokenDto);
            return modelMapper.set(tokenDto)
                    .field("userId", "user")
                    .convertFieldToType("user", User.class)
                    .getTransaction().mapTo(Token.class);
        } else {
            throw new EntityException("No token found");
        }
    }

    public Token delete(Token token) throws EntityException {
        final TokenDto tokenDto = tokenRepository.findById(token.getId());
        if (tokenDto != null) {
            tokenRepository.delete(tokenDto);
            return modelMapper.set(tokenDto)
                    .field("userId", "user")
                    .convertFieldToType("user", User.class)
                    .getTransaction().mapTo(Token.class);
        } else {
            throw new EntityException("No token found");
        }
    }
}
