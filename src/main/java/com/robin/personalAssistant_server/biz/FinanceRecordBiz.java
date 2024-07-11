package com.robin.personalAssistant_server.biz;

import com.robin.personalAssistant_server.entity.FinanceRecord;
import com.robin.personalAssistant_server.mapper.FinanceRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceRecordBiz {
    @Autowired
    private FinanceRecordMapper mapper;

    public void setMapper(FinanceRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<FinanceRecord> getFinanceRecordList() {
        return mapper.selectFinanceRecords();
    }

    public FinanceRecord getFinanceRecordById(int financeId) {
        return mapper.selectFinanceRecordById(financeId);
    }

    public boolean addFinanceRecord(FinanceRecord financeRecord) {
        return mapper.insertFinanceRecord(financeRecord) > 0;
    }

    public boolean removeFinanceRecordById(int financeId) {
        return mapper.deleteFinanceRecordById(financeId) > 0;
    }

    public boolean updateFinanceRecord(FinanceRecord financeRecord) {
        return mapper.updateFinanceRecord(financeRecord) > 0;
    }
}
