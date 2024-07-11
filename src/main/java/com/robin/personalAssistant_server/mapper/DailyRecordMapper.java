package com.robin.personalAssistant_server.mapper;

import com.robin.personalAssistant_server.entity.DailyRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DailyRecordMapper {
    //返回所有Daily的list
    @Select("select * from daily")
    List<DailyRecord> selectDailies();

    @Select("select * from daily where dailyId=#{dailyId}")
    DailyRecord selectDailyById(int dailyId);

    @Insert("insert into daily values (null, #{title}, #{content}, #{createTime})")
    int insertDaily(DailyRecord dailyRecord);

    @Delete("delete from daily where dailyId=#{dailyId}")
    int deleteDailyById(int dailyId);

    @Update("update daily set title=#{title}, content=#{content}, createTime=#{createTime} where dailyId=#{dailyId}")
    int updateDaily(DailyRecord updatedDaily);
}
