package io.ermdev.cshop.data.repository;

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

    @Insert("create table if not exists tbluser(id bigint not null auto_increment, name varchar(45), email " +
            "varchar(45), username varchar(45), password varchar(45), activated tinyint(1), primary key(id))")
    void createUserTable();

    @Insert("create table if not exists tblrole(id bigint not null auto_increment, name varchar(45), " +
            "primary key(id))")
    void createRoleTable();

    @Insert("create table if not exists tbluser_role(id bigint not null auto_increment, userId bigint not null, " +
            "roleId bigint not null, primary key(id), foreign key(userId) references tbluser(id) on delete cascade " +
            "on update cascade, foreign key(roleId) references tblrole(id) on delete cascade on update cascade)")
    void createUserRoleTable();

    @Insert("create table if not exists tblcategory(id bigint not null auto_increment, name varchar(45), description " +
            "varchar(200), parentId bigint default null, primary key(id), foreign key(parentId) references " +
            "tblcategory(id) on delete cascade on update cascade)")
    void createCategoryTable();

    @Insert("create table if not exists tbltag(id bigint not null auto_increment, title varchar(45), description " +
            "varchar(200), keyword varchar(45), primary key(id))")
    void createTagTable();

    @Insert("create table if not exists tblitem(id bigint not null auto_increment, name varchar(100), description " +
            "varchar(200), price decimal(11, 2), discount decimal(11, 2), categoryId bigint not null, primary " +
            "key(id), foreign key(categoryId) references tblcategory(id) on delete cascade on update cascade)")
    void createItemTable();

    @Insert("create table if not exists tblitem_tag(id bigint not null auto_increment, itemId bigint not null, " +
            "tagId bigint not null, primary key(id), foreign key(itemId) references tblitem(id) on delete " +
            "cascade on update cascade, foreign key(tagId) references tbltag(id) on delete cascade on " +
            "update cascade)")
    void createItemTagTable();

    @Insert("create table if not exists tblrelated_tag(id bigint not null auto_increment, tagId bigint not null, " +
            "relatedTagId bigint not null, primary key(id), foreign key(tagId) references tbltag(id) on delete " +
            "cascade on update cascade, foreign key(relatedTagId) references tbltag(id) on delete cascade on update " +
            "cascade)")
    void createRelatedTagTable();

    @Insert("create table if not exists tblattribute(id bigint not null auto_increment, title varchar(45), " +
            "content varchar(200), description varchar(200), type varchar(45), primary key(id))")
    void createAttributeTable();

    @Insert("create table if not exists tblitem_attribute(id bigint not null auto_increment, itemId bigint not " +
            "null, attributeId bigint not null, primary key(id), foreign key (itemId) references tblitem(id) " +
            "on delete cascade on update cascade, foreign key(attributeId) references tblattribute(id) on delete " +
            "cascade on update cascade)")
    void createItemAttributeTable();

    @Insert("create table if not exists tblitem_image(id bigint not null auto_increment, itemId bigint not null, " +
            "src varchar(200), primary key(id), foreign key(itemId) references tblitem(id) on delete cascade on " +
            "update cascade)")
    void createItemImageTable();

    @Insert("create table if not exists tblverification_token(id bigint not null auto_increment, token varchar(100), " +
            "expiryDate varchar(45), userId bigint not null, primary key(id), foreign key(userId) references " +
            "tbluser(id) on delete cascade on update cascade)")
    void createVerificationTokenTable();
}
