package com.eastnets.call_center_management_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Agent {

    private String agentID;
    private String agentName;
    private AgentStatus status;
    private long totalNumberOfCalls;
    private long totalTimeNotReady;
    private LocalDateTime statusChangeTimestamp;


    public long getStatusUpdateTime() {
        return ChronoUnit.SECONDS.between(statusChangeTimestamp, LocalDateTime.now());
    }

    public boolean isReady() {
        return this.status == AgentStatus.READY;
    }

    public void setReady(boolean ready) {
        this.status = ready ? AgentStatus.READY : AgentStatus.NOT_READY;
    }

    public String getFormattedStatusUpdateTime() {
        long seconds = ChronoUnit.SECONDS.between(statusChangeTimestamp, LocalDateTime.now());
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}
