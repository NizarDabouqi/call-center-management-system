package com.eastnets.call_center_management_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DailyAgentReport {

    private String reportID;
    private String agentID;
    private Date date;
    private long totalNumberOfCalls;
    private long totalTalkTime;
    private long longestTalkTime;
    private long shortestTalkTime;
    private long totalTimeNotReady;
    private double averageNumberOfCalls;

    public void calculateAverageCalls(long totalCalls, long days) {
        this.averageNumberOfCalls = totalCalls > 0 ? (double) totalCalls / days : 0;
    }

    public void updateTalkTime(long talkTime) {
        this.totalTalkTime += talkTime;
        if (talkTime > this.longestTalkTime) {
            this.longestTalkTime = talkTime;
        }
        if (talkTime < this.shortestTalkTime) {
            this.shortestTalkTime = talkTime;
        }
    }
}
