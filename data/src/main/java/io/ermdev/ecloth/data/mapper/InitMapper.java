package io.ermdev.ecloth.data.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InitMapper {

    @Insert("drop database if exists dbecloth")
    void dropDatabase();

    @Insert("create database if not exists dbecloth")
    void createDatabase();

    @Insert("use dbecloth")
    void useDatabase();

    @Insert("create table if not exists tbluser(id bigint not null auto_increment," +
            " username varchar(45), password varchar(45), primary key(id))")
    void createUserTableIfNotExist();
}
