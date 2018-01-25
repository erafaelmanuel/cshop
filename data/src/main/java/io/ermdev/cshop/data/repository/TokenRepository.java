package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TokenRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_token(id BIGINT NOT NULL AUTO_INCREMENT, key VARCHAR(100), expiryDate " +
            "VARCHAR(45), userId BIGINT, PRIMARY KEY(id), FOREIGN KEY(userId) REFERENCES tbl_user(id) ON DELETE " +
            "CASCADE ON UPDATE CASCADE)")
    void createTable();

    @Select("SELECT * FROM tbl_token WHERE id=#{tokenId}")
    Token findById(@Param("tokenId") Long tokenId);

    @Select("SELECT * FROM tbl_token")
    List<Token> findAll();

    @Select("SELECT * FROM tbl_token WHERE key=#{key}")
    Token findByKey(@Param("key") String key);

    @Select("SELECT * FROM tbl_token WHERE userId=#{userId} LIMIT 1")
    Token findByUserId(@Param("userId") Long userId);
}
