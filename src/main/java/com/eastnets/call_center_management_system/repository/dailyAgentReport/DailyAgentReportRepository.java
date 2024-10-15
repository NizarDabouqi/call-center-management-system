package com.eastnets.call_center_management_system.repository.dailyAgentReport;

import com.eastnets.call_center_management_system.model.DailyAgentReport;

import java.util.List;

public interface DailyAgentReportRepository {

    void saveOrUpdate(DailyAgentReport report);

    DailyAgentReport findByAgentId(String agentID);

    List<DailyAgentReport> findAll();

    void deleteAll();
}
