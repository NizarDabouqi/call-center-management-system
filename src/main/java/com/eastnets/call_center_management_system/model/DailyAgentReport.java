package com.eastnets.call_center_management_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DailyAgentReport {

    private String reportID;
    private String agentID;
    private long totalNumberOfCalls;
    private long totalTalkTime;
    private long longestTalkTime;
    private long shortestTalkTime;
    private long totalTimeNotReady;
    private double averageNumberOfCalls;
}
