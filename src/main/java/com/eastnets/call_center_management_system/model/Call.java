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
public class Call {

    private String callID;
    private String agentID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long durationInSeconds;

    public Call(String callID, String agentID) {
        this.callID = callID;
        this.agentID = agentID;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
        this.durationInSeconds = 0;
    }

    public String getFormattedDuration() {
        long minutes = durationInSeconds / 60;
        long seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
