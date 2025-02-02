package com.example.taskflow.controller;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskAssignment;
import com.example.taskflow.entity.User;
import com.example.taskflow.service.TaskAssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskAssignmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskAssignmentService taskAssignmentService;

    @InjectMocks
    private TaskAssignmentController taskAssignmentController;

    private TaskAssignment assignment1, assignment2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskAssignmentController).build();

        User user1 = new User();
        user1.setId(1L);

        Task task1 = new Task();
        task1.setId(101L);

        assignment1 = new TaskAssignment();
        assignment1.setId(1L);
        assignment1.setTask(task1);
        assignment1.setAssignee(user1);

        User user2 = new User();
        user2.setId(2L);

        Task task2 = new Task();
        task2.setId(102L);

        assignment2 = new TaskAssignment();
        assignment2.setId(2L);
        assignment2.setTask(task2);
        assignment2.setAssignee(user2);
    }

    @Test
    void testGetAllTaskAssignments() throws Exception {
        List<TaskAssignment> assignments = Arrays.asList(assignment1, assignment2);
        when(taskAssignmentService.getAllTaskAssignments()).thenReturn(assignments);

        mockMvc.perform(get("/task-assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(taskAssignmentService, times(1)).getAllTaskAssignments();
    }

    @Test
    void testCreateTaskAssignment() throws Exception {
        when(taskAssignmentService.createTaskAssignment(any(TaskAssignment.class))).thenReturn(assignment1);

        mockMvc.perform(post("/task-assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"task\":{\"id\":101},\"assignee\":{\"id\":1}}"))
                .andExpect(status().isOk());

        verify(taskAssignmentService, times(1)).createTaskAssignment(any(TaskAssignment.class));
    }

    @Test
    void testDeleteTaskAssignment() throws Exception {
        doNothing().when(taskAssignmentService).deleteTaskAssignment(1L);

        mockMvc.perform(delete("/task-assignments/1"))
                .andExpect(status().isOk());

        verify(taskAssignmentService, times(1)).deleteTaskAssignment(1L);
    }
}
