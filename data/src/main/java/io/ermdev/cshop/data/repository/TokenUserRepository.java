package io.ermdev.cshop.data.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenUserRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_token_user(id BIGINT NOT NULL AUTO_INCREMENT, tokenId BIGINT NOT NULL, " +
            "userId BIGINT NOT NULL UNIQUE, PRIMARY KEY(id), FOREIGN KEY(tokenId) REFERENCES tbl_token(id) ON " +
            "DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY(userId) REFERENCES tbl_user(id) ON DELETE CASCADE ON " +
            "UPDATE CASCADE)")
    void createTable();
}
