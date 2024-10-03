package com.eastnets.call_center_management_system.model;

import lombok.Getter;

@Getter
public enum AgentStatus {
    READY("Agent is ready to take a call."),
    NOT_READY("Agent is not ready, might be on break or unavailable."),
    ON_CALL("Agent is currently on a call.");

    private final String description;

    AgentStatus(String description) {
        this.description = description;
    }
}
