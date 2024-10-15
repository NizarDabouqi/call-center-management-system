package com.eastnets.call_center_management_system.repository.call;

import com.eastnets.call_center_management_system.model.Call;

import java.util.List;

public interface CallRepository {

    void saveCall(Call call);

    List<Call> findActiveCalls();

    void updateCallDuration(String callID, long duration);

    List<Call> findAllCalls();

    void updateCall(Call call);

    List<Call> findCallsByAgentId(String agentID);

    void deleteAll();
}
