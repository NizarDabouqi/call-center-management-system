package com.eastnets.call_center_management_system.repository.agent;

import com.eastnets.call_center_management_system.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AgentRepositoryImpl implements AgentRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Agent> findAllAgents() {
        String sql = "SELECT * FROM Agent";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Agent.class));
    }

    @Override
    public List<Agent> findAgentsByStatus(String status) {
        String sql = "SELECT * FROM Agent WHERE status = :status";

        Map<String, String> params = new HashMap<>();
        params.put("status", status);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Agent.class));
    }

    @Override
    public void updateAgentState(Agent agent) {
        String sql = "UPDATE Agent SET status = :status, totalNumberOfCalls = :totalNumberOfCalls, "
                + "totalTimeNotReady = :totalTimeNotReady, statusChangeTimestamp = :statusChangeTimestamp "
                + "WHERE AgentID = :agentID";

        Map<String, Object> params = new HashMap<>();
        params.put("status", agent.getStatus().name());
        params.put("totalNumberOfCalls", agent.getTotalNumberOfCalls());
        params.put("totalTimeNotReady", agent.getTotalTimeNotReady());
        params.put("statusChangeTimestamp", agent.getStatusChangeTimestamp());
        params.put("agentID", agent.getAgentID());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Agent findAgentById(String agentID) {
        String sql = "SELECT * FROM Agent WHERE AgentID = :agentID";

        Map<String, Object> params = new HashMap<>();
        params.put("agentID", agentID);

        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Agent.class));
    }

//    @Override
//    public void resetDailyAgentPerformance() {
//        String sql = "UPDATE Agent " +
//                "SET status = :status, " +
//                "totalNumberOfCalls = :totalNumberOfCalls, " +
//                "statusUpdateTime = :statusUpdateTime, " +
//                "totalTimeNotReady = :totalTimeNotReady";
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("status", "NOT_READY");
//        params.put("totalNumberOfCalls", 0);
//        params.put("statusUpdateTime", 0);
//        params.put("totalTimeNotReady", 0);
//
//        namedParameterJdbcTemplate.update(sql, params);
//    }
}
