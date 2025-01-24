package com.example.taskflow.controller;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import com.example.taskflow.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        task = new Task(1L, "Test Task", TaskStatus.TO_DO, "Test description");
    }

    @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("TO_DO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Test description"));
    }

    @Test
    public void testCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Task\", \"status\":\"TO_DO\", \"description\":\"Test description\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("TO_DO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test description"));
    }

    @Test
    public void testUpdateTaskStatus() throws Exception {
        Task updatedTask = new Task(1L, "Test Task", TaskStatus.IN_PROGRESS, "Test description");

        // Mock the updateTaskStatus method to return the task with updated status
        when(taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS)).thenReturn(updatedTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/1/status")
                        .param("status", "IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("TO_DO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test description"));
    }

    
    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Task with ID 1 deleted successfully."));
    }
}
