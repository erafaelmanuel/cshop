package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Image;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImageItemRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_image_item(id BIGINT NOT NULL AUTO_INCREMENT, imageId BIGINT NOT NULL, " +
            "itemId BIGINT NOT NULL, PRIMARY KEY(id), FOREIGN KEY(imageId) REFERENCES tbl_image(id) ON DELETE " +
            "CASCADE ON UPDATE CASCADE, FOREIGN KEY(itemId) REFERENCES tbl_item(id) ON DELETE CASCADE ON UPDATE " +
            "CASCADE)")
    void createTable();

    @Select("SELECT A.id, A.src FROM tbl_image AS A LEFT JOIN tbl_image_item AS B ON A.id=B.imageId WHERE " +
            "B.itemId=#{itemId}")
    List<Image> findImagesByItemId(@Param("itemId") Long itemId);

    @Select("SELECT A.id, A.src FROM tbl_image AS A LEFT JOIN tbl_image_item AS B ON A.id=B.imageId WHERE " +
            "B.itemId=#{itemId} AND B.imageId=#{imageId}")
    Image findImageByItemIdAndImageId(@Param("itemId") Long itemId, @Param("imageId") Long imageId);

    @Insert("INSERT INTO tbl_image_item(itemId, imageId) VALUES(#{itemId}, #{imageId})")
    void addImageToItem(@Param("itemId") Long itemId, @Param("imageId") Long imageId);

    @Delete("DELETE FROM tbl_image_item WHERE itemId=#{itemId} AND imageId=#{imageId}")
    void deleteImageFromItem(@Param("itemId") Long itemId, @Param("imageId") Long imageId);
}
