package io.ermdev.cshop.data.mapper;

import io.ermdev.cshop.model.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagRepository {

    @Select("select * from tbltag where id = #{tagId}")
    Tag findById(@Param("tagId") Long tagId);

    @Select("select _T.id as id, _T.title as title, _T.description as description, _T.keyword as keyword from " +
            "tblitem_tag as _IT join tbltag as _T on _IT.tagId=_T.id where _IT.itemId=#{itemId} group by " +
            "_IT.tagId")
    List<Tag> findByItemId(@Param("itemId") Long itemId);

    @Select("select * from tbltag")
    List<Tag> findAll();

    @Select("select _T.id as id, _T.title as title, _T.description as description, _T.keyword as keyword from " +
            "tblrelated_tag as _RT join tbltag as _T on _RT.relatedTagId=_T.id WHERE _RT.tagId=#{tagId}")
    List<Tag> findRelatedTag(@Param("tagId") Long tagId);

    @Insert("insert into tbltag(title, description, keyword) values(#{title}, #{description}, #{keyword})")
    void add(Tag tag);

    @Insert("insert into tblrelated_tag(tagId, relatedTagId) values(#{tagId}, #{relatedTagId})")
    void addRelatedTag(@Param("tagId") Long tagId, @Param("relatedTagId") Long relatedTagId);

    @Update("update tbltag set title=#{title}, description=#{description}, keyword=#{keyword} where id=#{id}")
    void updateById(Tag tag);

    @Delete("delete from tbltag where id=#{tagId}")
    void deleteById(Long tagId);

    @Delete("delete from tblrelated_tag where tagId=#{tagId} and relatedTagId=#{relatedTagId}")
    void deleteRelatedTag(@Param("tagId") Long tagId, @Param("relatedTagId") Long relatedTagId);
}
