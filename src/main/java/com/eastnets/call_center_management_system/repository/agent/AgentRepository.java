package com.eastnets.call_center_management_system.repository.agent;

import com.eastnets.call_center_management_system.model.Agent;

import java.util.List;

public interface AgentRepository {

    List<Agent> findAllAgents();

    List<Agent> findAgentsByStatus(String status);

    void updateAgentState(Agent agent);

    Agent findAgentById(String agentID);

    void updateAgentStatusTime(Agent agent);

    long getTotalNumberOfCallsByAgentId(String agentID);
}
