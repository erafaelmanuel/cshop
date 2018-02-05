package io.ermdev.cshop.data.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageItemRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_image_item(id BIGINT NOT NULL AUTO_INCREMENT, imageId BIGINT NOT NULL, " +
            "itemId BIGINT NOT NULL, PRIMARY KEY(id), FOREIGN KEY(imageId) REFERENCES tbl_image(id) ON DELETE " +
            "CASCADE ON UPDATE CASCADE, FOREIGN KEY(itemId) REFERENCES tbl_item(id) ON DELETE CASCADE ON UPDATE " +
            "CASCADE)")
    void createTable();
}
