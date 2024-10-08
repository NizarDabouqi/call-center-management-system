package com.eastnets.call_center_management_system.service.call;

import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallServiceImpl implements CallService {

    @Autowired
    private CallRepository callRepository;

    @Override
    public List<Call> getAllCalls() {
        return callRepository.findAllCalls();
    }
}

