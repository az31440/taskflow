package com.example.taskflow.controller;

import com.example.taskflow.entity.ActivityLog;
import com.example.taskflow.service.ActivityLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ActivityLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityLogService activityLogService;

    @InjectMocks
    private ActivityLogController activityLogController;

    private ActivityLog log1, log2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(activityLogController).build();

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
    void testGetAllActivityLogs() throws Exception {
        List<ActivityLog> logs = Arrays.asList(log1, log2);
        when(activityLogService.getAllActivityLogs()).thenReturn(logs);

        mockMvc.perform(get("/activity-logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].activity").value("Task Created"))
                .andExpect(jsonPath("$[1].activity").value("Task Updated"));

        verify(activityLogService, times(1)).getAllActivityLogs();
    }

    @Test
    void testCreateActivityLog() throws Exception {
        when(activityLogService.createActivityLog(any(ActivityLog.class))).thenReturn(log1);

        mockMvc.perform(post("/activity-logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"activity\":\"Task Created\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activity").value("Task Created"));

        verify(activityLogService, times(1)).createActivityLog(any(ActivityLog.class));
    }

    @Test
    void testDeleteActivityLog() throws Exception {
        doNothing().when(activityLogService).deleteActivityLog(1L);

        mockMvc.perform(delete("/activity-logs/1"))
                .andExpect(status().isOk());

        verify(activityLogService, times(1)).deleteActivityLog(1L);
    }
}
