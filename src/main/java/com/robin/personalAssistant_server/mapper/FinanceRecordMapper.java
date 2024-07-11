package com.robin.personalAssistant_server.mapper;

import com.robin.personalAssistant_server.entity.FinanceRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FinanceRecordMapper {

    @Select("select * from finance")
    List<FinanceRecord> selectFinanceRecords();

    @Select("select * from finance where financeId=#{financeId}")
    FinanceRecord selectFinanceRecordById(int financeId);

    @Insert("insert into finance values (null, #{financeType}, #{amount}, #{remark}, #{createTime})")
    int insertFinanceRecord(FinanceRecord financeRecord);

    @Delete("delete from finance where financeId=#{financeId}")
    int deleteFinanceRecordById(int financeId);

    @Update("update finance set financeType=#{financeType}, amount=#{amount}, remark=#{remark}, createTime=#{createTime} where financeId=#{financeId}")
    int updateFinanceRecord(FinanceRecord updatedFinanceRecord);
}
