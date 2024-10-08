package com.eastnets.call_center_management_system.service.initiateCall;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@EnableScheduling
public class InitiateCallServiceImpl implements InitiateCallService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CallRepository callRepository;

    private static long lastCallID = 0;

    @Scheduled(fixedRate = 10000)
    @Override
    public void insertCall() {
        List<Agent> readyAgents = agentRepository.findAgentsByStatus(AgentStatus.READY.name());

        if (readyAgents.isEmpty()) {
            return;
        }

        Random random = new Random();
        Agent assignedAgent = readyAgents.get(random.nextInt(readyAgents.size()));

        //String callID = System.currentTimeMillis() + "-" + random.nextInt(100);
        String callID = String.valueOf(++lastCallID);
        Call newCall = new Call(callID, assignedAgent.getAgentID());

        callRepository.saveCall(newCall);

        assignedAgent.setStatus(AgentStatus.ON_CALL);
        assignedAgent.setStatusUpdateTime(0);
        agentRepository.updateAgentState(assignedAgent);
    }
}
