package com.example.taskflow.service;

import com.example.taskflow.entity.ActivityLog;
import com.example.taskflow.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {
    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    public ActivityLog createActivityLog(ActivityLog activityLog) {
        activityLog.setTimestamp(LocalDateTime.now());
        return activityLogRepository.save(activityLog);
    }

    public void deleteActivityLog(Long id) {
        activityLogRepository.deleteById(id);
    }
}
