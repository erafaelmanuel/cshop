package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Token;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TokenRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_token(id BIGINT NOT NULL AUTO_INCREMENT, _key VARCHAR(100), expiryDate " +
            "VARCHAR(45), PRIMARY KEY(id))")
    void createTable();

    @Select("SELECT *, _key AS 'key' FROM tbl_token WHERE id=#{tokenId}")
    Token findById(@Param("tokenId") Long tokenId);

    @Select("SELECT *, _key AS 'key' FROM tbl_token WHERE _key=#{key}")
    Token findByKey(@Param("key") String key);

    @Select("SELECT *, _key AS 'key' FROM tbl_token")
    List<Token> findAll();

    @Insert("INSERT INTO tbl_token(id, _key, expiryDate) VALUES(#{id}, #{key}, #{expiryDate})")
    void add(Token token);

    @Update("UPDATE FROM tbl_token SET _key=#{key}, expiryDate=#{expiryDate} WHERE id=#{id}")
    void update(Token token);

    @Delete("DELETE FROM tbl_token WHERE id=#{id} OR _key=#{key}")
    void delete(Token token);
}
