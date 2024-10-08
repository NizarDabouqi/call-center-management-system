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
        String sql = "UPDATE Agent SET status = :status, statusUpdateTime = :statusUpdateTime, " +
                "totalNumberOfCalls = :totalNumberOfCalls WHERE AgentID = :agentID";

        Map<String, Object> params = new HashMap<>();
        params.put("status", agent.getStatus().name());
        params.put("statusUpdateTime", agent.getStatusUpdateTime());
        params.put("totalNumberOfCalls", agent.getTotalNumberOfCalls());
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

    @Override
    public void updateAgentStatusTime(Agent agent) {
        String sql = "UPDATE Agent SET statusUpdateTime = :statusUpdateTime WHERE AgentID = :agentID";

        Map<String, Object> params = new HashMap<>();
        params.put("statusUpdateTime", agent.getStatusUpdateTime());
        params.put("agentID", agent.getAgentID());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public long getTotalNumberOfCallsByAgentId(String agentID) {
        String sql = "SELECT totalNumberOfCalls FROM Agent WHERE AgentID = :agentID";

        Map<String, Object> params = new HashMap<>();
        params.put("agentID", agentID);

        Long totalCalls = namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);

        return totalCalls != null ? totalCalls : 0;
    }
}
