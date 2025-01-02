package com.eastnets.call_center_management_system.service.agent;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public List<Agent> getAllAgents() {
        List<Agent> agents = agentRepository.findAllAgents();

        for (Agent agent : agents) {
            if (agent.getStatusChangeTimestamp() == null) {
                agent.setStatusChangeTimestamp(LocalDateTime.now());
                agentRepository.updateAgentState(agent);
            }
        }
        return agents;
    }

    @Override
    public void toggleAgentStatus(String agentID) {
        Agent agent = agentRepository.findAgentById(agentID);
        long statusUpdateTime = agent.getStatusUpdateTime();
        agent.setStatusChangeTimestamp(LocalDateTime.now());

        if (agent.getStatus() == AgentStatus.READY) {
            agent.setStatus(AgentStatus.NOT_READY);

        } else if (agent.getStatus() == AgentStatus.NOT_READY) {
            agent.setTotalTimeNotReady(agent.getTotalTimeNotReady() + statusUpdateTime);
            agent.setStatus(AgentStatus.READY);
        }
        agentRepository.updateAgentState(agent);
    }
}


