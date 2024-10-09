package com.eastnets.call_center_management_system.service.dailyAgentReport;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.model.DailyAgentReport;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import com.eastnets.call_center_management_system.repository.dailyAgentReport.DailyAgentReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DailyAgentReportService implements AgentReportService {

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private CallRepository callRepository;
    @Autowired
    private DailyAgentReportRepository dailyAgentReportRepository;

    @Scheduled(fixedRate = 61000)
    @Override
    public void generateDailyAgentReport() {
        List<Agent> agents = agentRepository.findAllAgents();

        for (Agent agent : agents) {
            DailyAgentReport report = dailyAgentReportRepository.findByAgentId(agent.getAgentID());

            if (report == null) {
                report = new DailyAgentReport();
                report.setAgentID(agent.getAgentID());
            }

            report.setTotalNumberOfCalls(agent.getTotalNumberOfCalls());

            List<Call> calls = callRepository.findCallsByAgentId(agent.getAgentID());

            long totalTalkTime = 0;
            long longestTalkTime = 0;
            long shortestTalkTime = Long.MAX_VALUE;

            for (Call call : calls) {
                long durationInSeconds = call.getDurationInSeconds();
                totalTalkTime += durationInSeconds;

                if (durationInSeconds > 0) {
                    if (durationInSeconds > longestTalkTime) {
                        longestTalkTime = durationInSeconds;
                    }
                    if (durationInSeconds < shortestTalkTime) {
                        shortestTalkTime = durationInSeconds;
                    }
                }
            }

            if (shortestTalkTime == Long.MAX_VALUE) {
                shortestTalkTime = 0;
            }

            report.setTotalTalkTime(totalTalkTime);
            report.setLongestTalkTime(longestTalkTime);
            report.setShortestTalkTime(shortestTalkTime);
            report.setTotalTimeNotReady(calculateTotalTimeNotReady(agent));
            report.setAverageNumberOfCalls(calculateAverageNumberOfCalls(agent));

            dailyAgentReportRepository.saveOrUpdate(report);
        }
    }

    private long calculateTotalTimeNotReady(Agent agent) {
        return 0;
    }

    private double calculateAverageNumberOfCalls(Agent agent) {
        long totalCallsForAllAgents = agentRepository.findAllAgents()
                .stream()
                .mapToLong(Agent::getTotalNumberOfCalls)
                .sum();

        if (totalCallsForAllAgents == 0) {
            return 0.0;
        }

        double average = (double) agent.getTotalNumberOfCalls() / totalCallsForAllAgents;

        BigDecimal roundedAverage = BigDecimal.valueOf(average)
                .setScale(3, RoundingMode.HALF_UP);

        return roundedAverage.doubleValue();
    }
}
