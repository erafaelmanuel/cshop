package io.ermdev.cshop.data.mapper;

import io.ermdev.cshop.model.entity.VerificationToken;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface VerificationTokenRepository {

    @Select("select * from tblverification_token where id=#{verificationTokenId}")
    VerificationToken findById(@Param("verificationTokenId") Long verificationTokenId);

    @Select("select * from tblverification_token where token=#{token} limit 1")
    VerificationToken findByToken(@Param("token") String token);

    @Select("select * from tblverification_token where userId=#{userId} limit 1")
    VerificationToken findByUserId(@Param("userId") Long userId);

    @Select("select * from tblverification_token")
    List<VerificationToken> findAll();

    @Insert("insert into tblverification_token(token, expiryDate, userId) values(#{token}, #{expiryDate}, #{userId})")
    void add(@Param("token") String token, @Param("expiryDate") Date expiryDate, @Param("userId") Long userId);

    @Update("update tblverification_token set token=#{token}, userId=#{userId}, expiryDate=#{expiryDate} where " +
            "id=#{verificationTokenId}")
    void updateById(@Param("verificationTokenId") Long verificationTokenId, @Param("token") String token,
                    @Param("userId") Long userId, @Param("expiryDate") Date expiryDate);

    @Update("update tblverification_token set token=#{token}, userId=#{userId}, expiryDate=#{expiryDate} where " +
            "token=#{token}")
    void updateByToken(@Param("token") String token, @Param("userId") Long userId, @Param("expiryDate") Date expiryDate);

    @Delete("delete from tblverification_token where id=#{verificationTokenId}")
    void deleteById(@Param("verificationTokenId") Long verificationTokenId);

    @Delete("delete from tblverification_token where id=#{userId}")
    void deleteByUserId(@Param("userId") Long userId);
}
