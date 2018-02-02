package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TokenUserRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_token_user(id BIGINT NOT NULL AUTO_INCREMENT, tokenId BIGINT NOT NULL, " +
            "userId BIGINT NOT NULL UNIQUE, PRIMARY KEY(id), FOREIGN KEY(tokenId) REFERENCES tbl_token(id) ON " +
            "DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY(userId) REFERENCES tbl_user(id) ON DELETE CASCADE ON " +
            "UPDATE CASCADE)")
    void createTable();

    @Select("SELECT B.id, B.name, B.email, B.username, B.password, B.enabled FROM tbl_token_user AS A LEFT JOIN " +
            "tbl_user AS B ON A.userId=B.id WHERE A.tokenId=#{tokenId} LIMIT 1")
    User findUserByTokenId(@Param("tokenId") Long tokenId);

    @Select("SELECT B.id, B._key, B.expiryDate FROM tbl_token_user AS A LEFT JOIN tbl_token AS B ON A.tokenId=B.id " +
            "WHERE A.userId=#{userId} LIMIT 1")
    Token findTokenByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO tbl_token_user(tokenId, userId) VALUES(#{tokenId}, #{userId})")
    void addUserToToken(@Param("tokenId") Long tokenId, @Param("userId") Long userId);

    @Delete("DELETE FROM tbl_token_user WHERE tokenId=#{tokenId}")
    void deleteUserFromToken(@Param("tokenId") Long tokenId);
}
