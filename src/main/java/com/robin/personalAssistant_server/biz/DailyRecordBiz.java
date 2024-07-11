package com.robin.personalAssistant_server.biz;

import com.robin.personalAssistant_server.entity.DailyRecord;
import com.robin.personalAssistant_server.mapper.DailyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordBiz {
    @Autowired
    private DailyRecordMapper mapper;

    public void setMapper(DailyRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<DailyRecord> getDailiesList() {
        return mapper.selectDailies();
    }

    public DailyRecord getDailyById(int dailyId) {
        return mapper.selectDailyById(dailyId);
    }

    public boolean addDaily(DailyRecord dailyRecord) {
        return mapper.insertDaily(dailyRecord) > 0;
    }

    public boolean removeDailyById(int dailyId) {
        return mapper.deleteDailyById(dailyId) > 0;
    }

    public boolean updateDaily(DailyRecord updatedDaily) {
        return mapper.updateDaily(updatedDaily) > 0;
    }
}
