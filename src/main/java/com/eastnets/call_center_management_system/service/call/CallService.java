package com.eastnets.call_center_management_system.service.call;

import com.eastnets.call_center_management_system.model.Call;

import java.util.List;

public interface CallService {

    void trackCallDuration();

    List<Call> getAllCalls();
}
