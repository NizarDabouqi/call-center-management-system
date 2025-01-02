package com.eastnets.call_center_management_system.service.closeCall;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    @Value("${call.duration.threshold}")
    private long callDurationThreshold;

    @Value("${call.close.percentage}")
    private int callClosePercentage;

    @Scheduled(fixedRate = 60000)
    @Override
    public void closeCalls() {
        List<Call> activeCalls = callRepository.findActiveCalls();

        List<Call> longCalls = activeCalls.stream()
                .filter(call -> Duration.between(call.getStartTime(), LocalDateTime.now()).getSeconds() > callDurationThreshold)
                .sorted(Comparator.comparingLong(call -> Duration.between(((Call) call).getStartTime(), LocalDateTime.now()).getSeconds()).reversed())
                .collect(Collectors.toList());

        int numberOfCallsToClose = (int) Math.ceil(longCalls.size() * (callClosePercentage / 100.0));

        for (int i = 0; i < numberOfCallsToClose; i++) {
            Call call = longCalls.get(i);

            call.setEndTime(LocalDateTime.now());

            long finalDuration = Duration.between(call.getStartTime(), call.getEndTime()).getSeconds();
            call.setDurationInSeconds(finalDuration);

            call.setEndCallMessage("Closed by the system");

            callRepository.updateCallDuration(call.getCallID(), finalDuration);
            callRepository.updateCall(call);

            Agent agent = agentRepository.findAgentById(call.getAgentID());
            agent.setStatus(AgentStatus.READY);
            agent.setStatusChangeTimestamp(LocalDateTime.now());
            agent.setTotalNumberOfCalls(agent.getTotalNumberOfCalls() + 1);
            agentRepository.updateAgentState(agent);
        }
    }
}

