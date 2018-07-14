package com.rem.cs.data.jpa.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    @Query("select t from Token as t where t.key = :key")
    Optional<Token> findByKey(@Param("key") String key);
}
