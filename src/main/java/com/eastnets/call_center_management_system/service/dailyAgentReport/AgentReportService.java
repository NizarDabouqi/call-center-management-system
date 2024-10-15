package com.eastnets.call_center_management_system.service.dailyAgentReport;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AgentReportService {

    void generateDailyAgentsReport();

    public byte[] exportDailyReportToPDF() throws JRException, IOException;
}
