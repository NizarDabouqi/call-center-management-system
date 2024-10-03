package com.eastnets.call_center_management_system.service.call;

import com.eastnets.call_center_management_system.model.Call;
import com.eastnets.call_center_management_system.repository.call.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CallServiceImpl implements CallService {

    @Autowired
    private CallRepository callRepository;

    @Scheduled(fixedRate = 1000)
    @Override
    public void trackCallDuration() {
        List<Call> activeCalls = callRepository.findActiveCalls();

        for (Call call : activeCalls) {
            long duration = Duration.between(call.getStartTime(), LocalDateTime.now()).getSeconds();

            call.setDurationInSeconds(duration);
            callRepository.updateCallDuration(call.getCallID(), duration);
        }
    }

    @Override
    public List<Call> getAllCalls() {
        return callRepository.findAllCalls();
    }
}

