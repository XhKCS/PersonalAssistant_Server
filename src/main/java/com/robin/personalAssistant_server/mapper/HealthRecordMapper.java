package com.robin.personalAssistant_server.mapper;

import com.robin.personalAssistant_server.entity.HealthRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HealthRecordMapper {

    @Select("select * from health")
    List<HealthRecord> selectHealthRecords();

    @Select("select * from health where healthId=#{healthId}")
    HealthRecord selectHealthRecordById(int healthId);

    @Insert("insert into health values (null, #{height}, #{weight}, #{BMI}, #{conclusion}, #{createTime})")
    int insertHealthRecord(HealthRecord healthRecord);

    @Delete("delete from health where healthId=#{healthId}")
    int deleteHealthRecordById(int healthId);

    @Update("update health set height=#{height}, weight=#{weight}, BMI=#{BMI}, conclusion=#{conclusion}, createTime=#{createTime} where healthId=#{healthId}")
    int updateHealthRecord(HealthRecord updatedHealthRecord);
}
