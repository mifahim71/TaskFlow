package com.example.taskflow.controller;

import com.example.taskflow.dto.ProjectDto;
import com.example.taskflow.dto.ResProjectDto;
import com.example.taskflow.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody ResProjectDto resProjectDto){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            projectService.createProject(resProjectDto, email);
            return ResponseEntity.ok("Project created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects(){
        try{
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            List<ProjectDto> projects = projectService.getProjects(name);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectsById(@PathVariable Long id){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            ProjectDto project = projectService.getProjectById(id, email);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectsById(@PathVariable Long id, @RequestBody ResProjectDto resProjectDto){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            ProjectDto project = projectService.updateProject(id, email, resProjectDto);
            return ResponseEntity.ok(project);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            projectService.deleteProject(id, email);
            return ResponseEntity.ok("Project deleted Successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
