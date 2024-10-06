package com.eastnets.call_center_management_system.controller;

import com.eastnets.call_center_management_system.model.Agent;
import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.service.agent.AgentService;
import com.eastnets.call_center_management_system.service.call.CallService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("callCenterSystemController")
@SessionScoped
public class CallCenterSystemController implements Serializable {

    @Autowired
    private AgentService agentService;

    @Autowired
    private CallService callService;

    @Getter
    private List<Agent> agents;

    @Getter
    private List<Call> calls;

    @PostConstruct
    public void init() {
        agents = agentService.getAllAgents();
        calls = callService.getAllCalls();
    }

    public void refreshAgents() {
        agents = agentService.getAllAgents();
    }

    public void refreshCalls() {
        calls = callService.getAllCalls();
    }

    public void toggleAgentStatus(String agentID) {
        agentService.toggleAgentStatus(agentID);
        refreshAgents();
    }
}
