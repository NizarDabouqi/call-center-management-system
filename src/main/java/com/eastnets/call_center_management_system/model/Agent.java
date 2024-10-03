package com.eastnets.call_center_management_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Agent {

    private String agentID;
    private String agentName;
    private AgentStatus status;
    private long totalNumberOfCalls;
    private LocalDateTime statusUpdateTime;

    public Agent(String agentID, String agentName) {
        this.agentID = agentID;
        this.agentName = agentName;
        this.status = AgentStatus.READY;
        this.totalNumberOfCalls = 0;
        this.statusUpdateTime = LocalDateTime.now();
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
        this.statusUpdateTime = LocalDateTime.now();
    }
}
