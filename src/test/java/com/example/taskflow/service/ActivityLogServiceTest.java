package com.example.taskflow.service;

import com.example.taskflow.entity.ActivityLog;
import com.example.taskflow.repository.ActivityLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityLogServiceTest {

    @Mock
    private ActivityLogRepository activityLogRepository;

    @InjectMocks
    private ActivityLogService activityLogService;

    private ActivityLog log1, log2;

    @BeforeEach
    void setUp() {
        log1 = new ActivityLog();
        log1.setId(1L);
        log1.setActivity("Task Created");
        log1.setTimestamp(LocalDateTime.now());

        log2 = new ActivityLog();
        log2.setId(2L);
        log2.setActivity("Task Updated");
        log2.setTimestamp(LocalDateTime.now());
    }

    @Test
    void testGetAllActivityLogs() {
        when(activityLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<ActivityLog> logs = activityLogService.getAllActivityLogs();

        assertEquals(2, logs.size());
        verify(activityLogRepository, times(1)).findAll();
    }

    @Test
    void testCreateActivityLog() {
        when(activityLogRepository.save(any(ActivityLog.class))).thenReturn(log1);

        ActivityLog createdLog = activityLogService.createActivityLog(log1);

        assertNotNull(createdLog);
        assertEquals("Task Created", createdLog.getActivity());
        assertNotNull(createdLog.getTimestamp());
    }

    @Test
    void testDeleteActivityLog() {
        doNothing().when(activityLogRepository).deleteById(1L);

        assertDoesNotThrow(() -> activityLogService.deleteActivityLog(1L));

        verify(activityLogRepository, times(1)).deleteById(1L);
    }
}
