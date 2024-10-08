package com.eastnets.call_center_management_system.service.agent;

import com.eastnets.call_center_management_system.model.Agent;

import java.util.List;

public interface AgentService {

    List<Agent> getAllAgents();

    void trackAgentStatusUpdateTime();

    void toggleAgentStatus(String agentID);
}
