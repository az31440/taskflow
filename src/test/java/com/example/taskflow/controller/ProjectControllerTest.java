package com.example.taskflow.controller;

import com.example.taskflow.entity.Project;
import com.example.taskflow.service.ProjectService;
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
class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private Project project1, project2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

        project1 = new Project();
        project1.setId(1L);
        project1.setName("Project Alpha");

        project2 = new Project();
        project2.setId(2L);
        project2.setName("Project Beta");
    }

    @Test
    void testGetAllProjects() throws Exception {
        List<Project> projects = Arrays.asList(project1, project2);
        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Project Alpha"))
                .andExpect(jsonPath("$[1].name").value("Project Beta"));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testGetProjectById() throws Exception {
        when(projectService.getProjectById(1L)).thenReturn(project1);

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Project Alpha"));

        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void testCreateProject() throws Exception {
        when(projectService.createProject(any(Project.class))).thenReturn(project1);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Project Alpha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Project Alpha"));

        verify(projectService, times(1)).createProject(any(Project.class));
    }

    @Test
    void testUpdateProject() throws Exception {
        Project updatedProject = new Project();
        updatedProject.setName("Updated Alpha");

        when(projectService.updateProject(eq(1L), any(Project.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Alpha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Alpha"));

        verify(projectService, times(1)).updateProject(eq(1L), any(Project.class));
    }

    @Test
    void testDeleteProject() throws Exception {
        doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(1L);
    }
}
