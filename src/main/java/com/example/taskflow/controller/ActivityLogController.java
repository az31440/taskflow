package com.example.taskflow.controller;

import com.example.taskflow.entity.ActivityLog;
import com.example.taskflow.service.ActivityLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity-logs")
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public List<ActivityLog> getAllActivityLogs() {
        return activityLogService.getAllActivityLogs();
    }

    @PostMapping
    public ActivityLog createActivityLog(@RequestBody ActivityLog activityLog) {
        return activityLogService.createActivityLog(activityLog);
    }

    @DeleteMapping("/{id}")
    public void deleteActivityLog(@PathVariable Long id) {
        activityLogService.deleteActivityLog(id);
    }
}
