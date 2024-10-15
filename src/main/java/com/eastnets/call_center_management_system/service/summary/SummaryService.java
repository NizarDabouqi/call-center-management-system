package com.eastnets.call_center_management_system.service.summary;

public interface SummaryService {

    int getTotalAgents();

    int getAgentsOnCall();

    int getAgentsReady();

    int getAgentsNotReady();

    String getAverageTalkTime();

    String getLongestTalkTime();

    long getTotalCallsToday();
}
