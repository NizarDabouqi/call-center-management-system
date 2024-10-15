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
    public synchronized void trackAgentStatusUpdateTime() {
        List<Agent> agents = getAllAgents();

        for (Agent agent : agents) {
            agent.setStatusUpdateTime(agent.getStatusUpdateTime() + 1);
            agentRepository.updateAgentStatusTime(agent);
        }
    }

    @Override
    public synchronized void toggleAgentStatus(String agentID) {
        Agent agent = agentRepository.findAgentById(agentID);
        agent.setStatusUpdateTime(0);

        if (!agent.getStatus().equals(AgentStatus.ON_CALL)) {
            if (agent.getStatus() == AgentStatus.READY) {
                agent.setStatus(AgentStatus.NOT_READY);
                System.out.println("Status changed to NOT_READY");

            } else if (agent.getStatus() == AgentStatus.NOT_READY) {
                agent.setTotalTimeNotReady(agent.getTotalTimeNotReady() + agent.getStatusUpdateTime());
                agent.setStatus(AgentStatus.READY);
                System.out.println("Status changed to READY");
            }
            agentRepository.updateAgentState(agent);
        }
    }
}

