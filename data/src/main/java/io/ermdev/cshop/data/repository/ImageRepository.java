package io.ermdev.cshop.data.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Deprecated
@Mapper
public interface ImageRepository {

    @Select("select src from tblitem_image where itemId=#{itemId}")
    List<String> findByItemId(@Param("itemId") Long itemId);
}
