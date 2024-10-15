package com.eastnets.call_center_management_system.service.summary;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.AgentStatus;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import com.eastnets.call_center_management_system.repository.dailyAgentReport.DailyAgentReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private DailyAgentReportRepository dailyAgentReportRepository;

    @Override
    public int getTotalAgents() {
        List<Agent> agents = agentRepository.findAllAgents();
        return agents.size();
    }

    @Override
    public int getAgentsOnCall() {
        return (int) agentRepository.findAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.ON_CALL)
                .count();
    }

    @Override
    public int getAgentsReady() {
        return (int) agentRepository.findAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.READY)
                .count();
    }

    @Override
    public int getAgentsNotReady() {
        return (int) agentRepository.findAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.NOT_READY)
                .count();
    }

    @Override
    public String getAverageTalkTime() {
        List<Call> completedCalls = callRepository.findAllCalls().stream()
                .filter(call -> call.getEndTime() != null)
                .collect(Collectors.toList());

        if (completedCalls.isEmpty()) {
            return "00:00:00";
        }

        long totalDurationInSeconds = completedCalls.stream()
                .mapToLong(Call::getDurationInSeconds)
                .sum();

        long averageDurationInSeconds = totalDurationInSeconds / completedCalls.size();

        long hours = averageDurationInSeconds / 3600;
        long minutes = (averageDurationInSeconds % 3600) / 60;
        long seconds = averageDurationInSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getLongestTalkTime() {
        long longestDurationInSeconds = callRepository.findAllCalls().stream()
                .filter(call -> call.getEndTime() != null)
                .mapToLong(Call::getDurationInSeconds)
                .max()
                .orElse(0);

        long hours = longestDurationInSeconds / 3600;
        long minutes = (longestDurationInSeconds % 3600) / 60;
        long seconds = longestDurationInSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    @Override
    public long getTotalCallsToday() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        return (int) callRepository.findAllCalls().stream()
                .filter(call -> call.getStartTime().isAfter(startOfDay))
                .count();
    }

    @Scheduled(cron = "0 */3 * * * ?") // @Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyData() {
        callRepository.deleteAll();
        dailyAgentReportRepository.deleteAll();
        agentRepository.resetDailyAgentPerformance();
    }
}

