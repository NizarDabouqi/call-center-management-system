package com.eastnets.call_center_management_system.controller;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.service.agent.AgentService;
import com.eastnets.call_center_management_system.service.call.CallService;
import com.eastnets.call_center_management_system.service.dailyAgentReport.AgentReportService;
import com.eastnets.call_center_management_system.service.summary.SummaryService;
import lombok.Getter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("callCenterSystemController")
@SessionScoped
public class CallCenterSystemController implements Serializable {

    @Autowired
    private AgentService agentService;

    @Autowired
    private CallService callService;

    @Autowired
    private AgentReportService agentReportService;

    @Autowired
    private SummaryService summaryService;

    @Getter
    private List<Agent> agents;

    @Getter
    private List<Call> calls;

    @Getter
    private int totalAgents;

    @Getter
    private int agentsOnCall;

    @Getter
    private int agentsReady;

    @Getter
    private int agentsNotReady;

    @Getter
    private PieChartModel pieModel;

    @Getter
    private String averageTalkTime;

    @Getter
    private String longestTalkTime;

    @Getter
    private long totalCallsToday;

    @PostConstruct
    public void init() {
        agents = agentService.getAllAgents();
        calls = callService.getAllCalls();
        loadAgentSummary();
        loadCallSummary();
        createPieModel();
    }

    public void refreshAgents() {
        agents = agentService.getAllAgents();
    }

    public void refreshChart() {
        loadAgentSummary();
        createPieModel();
    }

    public void refreshSummary() {
        averageTalkTime = summaryService.getAverageTalkTime();
        longestTalkTime = summaryService.getLongestTalkTime();
        totalCallsToday = summaryService.getTotalCallsToday();
    }

    public void toggleAgentStatus(String agentID) {
        agentService.toggleAgentStatus(agentID);
        refreshAgents();
    }

    public void exportDailyReport() throws IOException, JRException {
        byte[] pdfData = agentReportService.exportDailyReportToPDF();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Daily_agents_report.pdf\"");
        externalContext.setResponseContentLength(pdfData.length);

        externalContext.getResponseOutputStream().write(pdfData);
        facesContext.responseComplete();
    }

    public void loadAgentSummary() {
        totalAgents = summaryService.getTotalAgents();
        agentsOnCall = summaryService.getAgentsOnCall();
        agentsReady = summaryService.getAgentsReady();
        agentsNotReady = summaryService.getAgentsNotReady();
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        pieModel.set("On Call", agentsOnCall);
        pieModel.set("Ready", agentsReady);
        pieModel.set("Not Ready", agentsNotReady);
        pieModel.setTitle("Agent Status Overview");
        pieModel.setLegendPosition("w");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
    }

    public void loadCallSummary() {
        averageTalkTime = summaryService.getAverageTalkTime();
        longestTalkTime = summaryService.getLongestTalkTime();
        totalCallsToday = summaryService.getTotalCallsToday();
    }
}
