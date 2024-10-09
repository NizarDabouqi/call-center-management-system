package com.eastnets.call_center_management_system.repository.call;

import com.eastnets.call_center_management_system.model.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CallRepositoryImpl implements CallRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveCall(Call call) {
        String sql = "INSERT INTO call (callID, agentID, startTime, endTime, durationInSeconds, endCallMessage) " +
                "VALUES (:callID, :agentID, :startTime, :endTime, :durationInSeconds, :endCallMessage)";

        Map<String, Object> params = new HashMap<>();
        params.put("callID", call.getCallID());
        params.put("agentID", call.getAgentID());
        params.put("startTime", call.getStartTime());
        params.put("endTime", call.getEndTime());
        params.put("durationInSeconds", call.getDurationInSeconds());
        params.put("endCallMessage", call.getEndCallMessage());

        namedParameterJdbcTemplate.update(sql, params);
    }


    @Override
    public List<Call> findActiveCalls() {
        String sql = "SELECT * FROM Call WHERE endTime IS NULL";

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Call.class));
    }

    @Override
    public void updateCallDuration(String callID, long duration) {
        String sql = "UPDATE Call SET durationInSeconds = :duration WHERE callID = :callID";
        Map<String, Object> params = new HashMap<>();
        params.put("duration", duration);
        params.put("callID", callID);

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Call> findAllCalls() {
        String sql = "SELECT * FROM Call";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Call.class));
    }

    @Override
    public void updateCall(Call call) {
        String sql = "UPDATE Call SET endTime = :endTime, durationInSeconds = :durationInSeconds, endCallMessage = :endCallMessage WHERE callID = :callID";

        Map<String, Object> params = new HashMap<>();
        params.put("endTime", call.getEndTime());
        params.put("durationInSeconds", call.getDurationInSeconds());
        params.put("endCallMessage", call.getEndCallMessage());
        params.put("callID", call.getCallID());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Call> findCallsByAgentId(String agentID) {
        String sql = "SELECT * FROM Call WHERE agentID = :agentID";

        Map<String, String> params = new HashMap<>();
        params.put("agentID", agentID);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Call.class));
    }
}
