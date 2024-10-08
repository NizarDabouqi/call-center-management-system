package com.eastnets.call_center_management_system.service.summary;

import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.service.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private AgentService agentService;

    @Override
    public int getTotalAgents() {
        return agentService.getAllAgents().size();
    }

    @Override
    public int getReadyAgents() {
        return (int) agentService.getAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.READY)
                .count();
    }

    @Override
    public int getNotReadyAgents() {
        return (int) agentService.getAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.NOT_READY)
                .count();
    }

    @Override
    public int getOnCallAgents() {
        return (int) agentService.getAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.ON_CALL)
                .count();
    }
}

