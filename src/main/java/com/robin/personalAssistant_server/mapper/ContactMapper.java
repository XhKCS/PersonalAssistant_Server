package com.robin.personalAssistant_server.mapper;

import com.robin.personalAssistant_server.entity.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContactMapper {

    @Select("select * from contact")
    List<Contact> selectContacts();

    @Select("select * from contact where contactName=#{contactName}")
    List<Contact> selectContactsByName(String contactName);

    @Select("select * from contact where contactId=#{contactId}")
    Contact selectContactById(int contactId);

    @Select("select * from contact where phoneNumber=#{phoneNumber}")
    Contact selectContactByPhoneNumber(String phoneNumber);

    @Insert("insert into contact values (null, #{contactName}, #{phoneNumber}, #{createTime})")
    int insertContact(Contact contact);

    @Delete("delete from contact where contactId=#{contactId}")
    int deleteContactById(int contactId);

    @Update("update contact set contactName=#{contactName}, phoneNumber=#{phoneNumber}, createTime=#{createTime} where contactId=#{contactId}")
    int updateContact(Contact updatedContact);
}
