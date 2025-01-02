package com.eastnets.call_center_management_system.service.dailyAgentReport;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.model.DailyAgentReport;
import com.eastnets.call_center_management_system.repository.agent.AgentRepository;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import com.eastnets.call_center_management_system.repository.dailyAgentReport.DailyAgentReportRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void generateDailyAgentsReport() {
        List<Agent> agents = agentRepository.findAllAgents();

        for (Agent agent : agents) {
            DailyAgentReport report = dailyAgentReportRepository.findByAgentId(agent.getAgentID());

            if (report == null) {
                report = new DailyAgentReport();
                report.setAgentID(agent.getAgentID());
            }

            report.setAgentName(agent.getAgentName());
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
            report.setTotalTimeNotReady(agent.getTotalTimeNotReady());
            report.setAverageNumberOfCalls(calculateAverageNumberOfCalls(agent));

            dailyAgentReportRepository.saveOrUpdate(report);
        }
    }

    @Override
    public byte[] exportDailyReportToPDF() throws JRException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dailyAgentReport.jrxml");

        if (inputStream == null) {
            throw new FileNotFoundException("Jasper report file not found.");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        List<DailyAgentReport> reports = dailyAgentReportRepository.findAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reports);

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
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
