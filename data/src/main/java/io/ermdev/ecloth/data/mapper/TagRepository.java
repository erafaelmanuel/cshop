package io.ermdev.ecloth.data.mapper;

import io.ermdev.ecloth.model.entity.Category;
import io.ermdev.ecloth.model.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagRepository {

    @Select("select * from tbltag where id = #{tagId}")
    Tag findById(@Param("tagId") Long tagId);

    @Select("select _T.id as id, _T.name as name, _T.value as value from tblitem_tag as _IT join tbltag " +
            "as _T on _IT.categoryId=_T.id where _IT.itemId = #{itemId} group by _IT.categoryId")
    List<Tag> findByItemId(@Param("itemId") Long itemId);

    @Select("select * from tbltag")
    List<Tag> findAll();

    @Insert("insert into tbltag(name, value) values(#{name}, #{value})")
    void add(Tag tag);

    @Update("update tbltag set name=#{name}, value=#{value} where id=#{id}")
    void updateById(Tag tag);

    @Delete("delete from tbltag where id=#{tagId}")
    void deleteById(Long tagId);
}
