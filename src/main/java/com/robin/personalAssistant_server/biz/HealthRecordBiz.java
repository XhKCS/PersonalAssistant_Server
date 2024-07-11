package com.robin.personalAssistant_server.biz;

import com.robin.personalAssistant_server.entity.HealthRecord;
import com.robin.personalAssistant_server.mapper.HealthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthRecordBiz {
    @Autowired
    private HealthRecordMapper mapper;

    public void setMapper(HealthRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<HealthRecord> getHealthRecordList() {
        return mapper.selectHealthRecords();
    }

    public HealthRecord getHealthRecordById(int healthId) {
        return mapper.selectHealthRecordById(healthId);
    }

    public boolean addHealthRecord(HealthRecord healthRecord) {
        return mapper.insertHealthRecord(healthRecord) > 0;
    }

    public boolean removeHealthRecordById(int healthId) {
        return mapper.deleteHealthRecordById(healthId) > 0;
    }

    public boolean updateHealthRecord(HealthRecord healthRecord) {
        return mapper.updateHealthRecord(healthRecord) > 0;
    }
}
