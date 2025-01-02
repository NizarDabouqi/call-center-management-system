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
    private String agentName;
    private long totalNumberOfCalls;
    private long totalTalkTime;
    private long longestTalkTime;
    private long shortestTalkTime;
    private long totalTimeNotReady;
    private double averageNumberOfCalls;

    public String getFormattedTotalTalkTime() {
        return formatTime(totalTalkTime);
    }

    public String getFormattedLongestTalkTime() {
        return formatTime(longestTalkTime);
    }

    public String getFormattedShortestTalkTime() {
        return formatTime(shortestTalkTime);
    }

    public String getFormattedTotalTimeNotReady() {
        return formatTime(totalTimeNotReady);
    }


    private String formatTime(long timeInSeconds) {
        long hours = timeInSeconds / 3600;
        long minutes = (timeInSeconds % 3600) / 60;
        long seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
