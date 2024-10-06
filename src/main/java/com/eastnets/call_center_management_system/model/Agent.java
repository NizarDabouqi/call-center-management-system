package com.eastnets.call_center_management_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Agent {

    private String agentID;
    private String agentName;
    private AgentStatus status;
    private long totalNumberOfCalls;
    private long statusUpdateTime;

    public Agent(String agentID, String agentName) {
        this.agentID = agentID;
        this.agentName = agentName;
        this.status = AgentStatus.READY;
        this.totalNumberOfCalls = 0;
        this.statusUpdateTime = 0;
    }

    public String getFormattedStatusUpdateTime() {
        long hours = statusUpdateTime / 3600;
        long minutes = (statusUpdateTime % 3600) / 60;
        long seconds = statusUpdateTime % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public boolean isReady() {
        return this.status == AgentStatus.READY;
    }

    public void setReady(boolean ready) {
        this.status = ready ? AgentStatus.READY : AgentStatus.NOT_READY;
    }
}
