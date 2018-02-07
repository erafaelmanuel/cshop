package io.ermdev.cshop.data.repository;

import io.ermdev.cshop.data.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImageRepository {

    @Insert("CREATE TABLE IF NOT EXISTS tbl_image(id BIGINT NOT NULL AUTO_INCREMENT, src VARCHAR(200), PRIMARY " +
            "KEY(id))")
    void createTable();

    @Select("SELECT * FROM tbl_image WHERE id=#{imageId}")
    Image findById(@Param("imageId") Long imageId);

    @Select("SELECT * FROM tbl_image")
    List<Image> findAll();

    @Insert("INSERT INTO tbl_image(src) VALUES(#{src})")
    void add(Image image);
}
