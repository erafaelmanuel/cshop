package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImageItemRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_image_item(id BIGINT NOT NULL AUTO_INCREMENT, imageId BIGINT NOT NULL, " +
            "itemId BIGINT NOT NULL, PRIMARY KEY(id), FOREIGN KEY(imageId) REFERENCES tbl_image(id) ON DELETE " +
            "CASCADE ON UPDATE CASCADE, FOREIGN KEY(itemId) REFERENCES tbl_item(id) ON DELETE CASCADE ON UPDATE " +
            "CASCADE)")
    void createTable();

    @Select("SELECT A.id, B.src FROM tbl_image AS A LEFT JOIN tbl_image_item AS B ON A.id=B.imageId WHERE " +
            "B.itemId=#{itemId}")
    List<Image> findImagesByItemId(@Param("itemId") Long itemId);
}
