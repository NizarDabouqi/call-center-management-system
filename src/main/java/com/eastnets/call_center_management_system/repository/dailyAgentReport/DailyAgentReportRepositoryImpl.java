package com.eastnets.call_center_management_system.repository.dailyAgentReport;

import com.eastnets.call_center_management_system.model.DailyAgentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DailyAgentReportRepositoryImpl implements DailyAgentReportRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveOrUpdate(DailyAgentReport report) {
        String findSql = "SELECT COUNT(*) FROM DailyAgentReport WHERE agentID = :agentID";

        Map<String, Object> findParams = new HashMap<>();
        findParams.put("agentID", report.getAgentID());

        Integer count = namedParameterJdbcTemplate.queryForObject(findSql, findParams, Integer.class);

        if (count != null && count > 0) {
            String updateSql = "UPDATE DailyAgentReport SET totalNumberOfCalls = :totalNumberOfCalls, " +
                    "totalTalkTime = :totalTalkTime, longestTalkTime = :longestTalkTime, " +
                    "shortestTalkTime = :shortestTalkTime, totalTimeNotReady = :totalTimeNotReady, " +
                    "averageNumberOfCalls = :averageNumberOfCalls WHERE agentID = :agentID";

            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("totalNumberOfCalls", report.getTotalNumberOfCalls());
            updateParams.put("totalTalkTime", report.getTotalTalkTime());
            updateParams.put("longestTalkTime", report.getLongestTalkTime());
            updateParams.put("shortestTalkTime", report.getShortestTalkTime());
            updateParams.put("totalTimeNotReady", report.getTotalTimeNotReady());
            updateParams.put("averageNumberOfCalls", report.getAverageNumberOfCalls());
            updateParams.put("agentID", report.getAgentID());

            namedParameterJdbcTemplate.update(updateSql, updateParams);

        } else {
            String insertSql = "INSERT INTO DailyAgentReport (agentID, totalNumberOfCalls, totalTalkTime, " +
                    "longestTalkTime, shortestTalkTime, totalTimeNotReady, averageNumberOfCalls) " +
                    "VALUES (:agentID, :totalNumberOfCalls, :totalTalkTime, :longestTalkTime, :shortestTalkTime, " +
                    ":totalTimeNotReady, :averageNumberOfCalls)";

            Map<String, Object> insertParams = new HashMap<>();
            insertParams.put("agentID", report.getAgentID());
            insertParams.put("totalNumberOfCalls", report.getTotalNumberOfCalls());
            insertParams.put("totalTalkTime", report.getTotalTalkTime());
            insertParams.put("longestTalkTime", report.getLongestTalkTime());
            insertParams.put("shortestTalkTime", report.getShortestTalkTime());
            insertParams.put("totalTimeNotReady", report.getTotalTimeNotReady());
            insertParams.put("averageNumberOfCalls", report.getAverageNumberOfCalls());

            namedParameterJdbcTemplate.update(insertSql, insertParams);
        }
    }

    @Override
    public DailyAgentReport findByAgentId(String agentID) {
        String sql = "SELECT * FROM DailyAgentReport WHERE agentID = :agentID";
        Map<String, Object> params = new HashMap<>();
        params.put("agentID", agentID);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(DailyAgentReport.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<DailyAgentReport> findAll() {
        String sql = "SELECT * FROM DailyAgentReport";

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DailyAgentReport.class));
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM DailyAgentReport";
        namedParameterJdbcTemplate.update(sql, new HashMap<>());
    }
}

