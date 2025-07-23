package com.example.taskflow.controller;

import com.example.taskflow.dto.ResTaskDto;
import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.entity.Task;
import com.example.taskflow.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody ResTaskDto resTaskDto){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            TaskDto task = taskService.createTask(resTaskDto, email);
            return ResponseEntity.ok(task);
        } catch (AccessDeniedException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTask(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            List<TaskDto> allTasks = taskService.getAllTasks(email);
            return ResponseEntity.ok(allTasks);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            TaskDto task = taskService.getTask(id, email);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody ResTaskDto resTaskDto){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            TaskDto task = taskService.updateTask(id, email, resTaskDto);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            taskService.deleteTask(id, email);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
