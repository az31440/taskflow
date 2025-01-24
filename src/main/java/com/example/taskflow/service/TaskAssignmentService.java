package com.example.taskflow.service;

import com.example.taskflow.entity.TaskAssignment;
import com.example.taskflow.repository.TaskAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskAssignmentService {
    private final TaskAssignmentRepository taskAssignmentRepository;

    public TaskAssignmentService(TaskAssignmentRepository taskAssignmentRepository) {
        this.taskAssignmentRepository = taskAssignmentRepository;
    }

    public List<TaskAssignment> getAllTaskAssignments() {
        return taskAssignmentRepository.findAll();
    }

    public TaskAssignment createTaskAssignment(TaskAssignment taskAssignment) {
        return taskAssignmentRepository.save(taskAssignment);
    }

    public void deleteTaskAssignment(Long id) {
        taskAssignmentRepository.deleteById(id);
    }
}
