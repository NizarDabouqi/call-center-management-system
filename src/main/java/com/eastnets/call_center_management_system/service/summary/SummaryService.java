package com.eastnets.call_center_management_system.service.summary;

public interface SummaryService {

    int getTotalAgents();

    int getReadyAgents();

    int getNotReadyAgents();

    int getOnCallAgents();
}
