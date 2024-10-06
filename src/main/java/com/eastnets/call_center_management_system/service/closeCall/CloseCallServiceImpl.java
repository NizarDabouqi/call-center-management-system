package com.eastnets.call_center_management_system.service.closeCall;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CloseCallServiceImpl implements CloseCallService {

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Override
    @Scheduled(fixedRate = 60000)
    public void closeCalls() {
        List<Call> activeCalls = callRepository.findActiveCalls();

        List<Call> longCalls = activeCalls.stream()
                .filter(call -> call.getDurationInSeconds() > 10)
                .sorted(Comparator.comparingLong(Call::getDurationInSeconds).reversed())
                .collect(Collectors.toList());

        int numberOfCallsToClose = (int) Math.ceil(longCalls.size() * 0.75);

        for (int i = 0; i < numberOfCallsToClose; i++) {
            Call call = longCalls.get(i);

            Agent agent = agentRepository.findAgentById(call.getAgentID());
            agent.setStatus(AgentStatus.READY);
            agent.setStatusUpdateTime(0);
            agent.setTotalNumberOfCalls(agent.getTotalNumberOfCalls() + 1);
            agentRepository.updateAgentState(agent);

            call.setEndTime(LocalDateTime.now());
            callRepository.updateCall(call);
        }
    }
}

