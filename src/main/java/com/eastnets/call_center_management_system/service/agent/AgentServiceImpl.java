package com.eastnets.call_center_management_system.service.agent;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAllAgents();
    }

    @Scheduled(fixedRate = 1000)
    @Override
    public void trackAgentStatusUpdateTime() {
        List<Agent> agents = getAllAgents();

        for (Agent agent : agents) {
            agent.setStatusUpdateTime(agent.getStatusUpdateTime() + 1);
            agentRepository.updateAgentStatusTime(agent);
        }
    }

    @Override
    public void toggleAgentStatus(String agentID) {
        Agent agent = agentRepository.findAgentById(agentID);

        if (!agent.getStatus().equals(AgentStatus.ON_CALL)) {
            if (agent.getStatus() == AgentStatus.READY) {
                agent.setStatus(AgentStatus.NOT_READY);
            } else {
                agent.setStatus(AgentStatus.READY);
            }

            agent.setStatusUpdateTime(0);
            agentRepository.updateAgentState(agent);
        }
    }
}

