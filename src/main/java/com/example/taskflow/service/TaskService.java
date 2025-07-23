package com.example.taskflow.service;

import com.example.taskflow.dto.ResTaskDto;
import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.entity.Project;
import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.User;
import com.example.taskflow.enums.TaskStatus;
import com.example.taskflow.mapper.TaskMapper;
import com.example.taskflow.repository.ProjectRepo;
import com.example.taskflow.repository.TaskRepo;
import com.example.taskflow.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;

    private final UserRepo userRepo;

    private final ProjectRepo projectRepo;

    private final TaskMapper taskMapper;


    public TaskDto createTask(ResTaskDto resTaskDto, String email) throws AccessDeniedException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        Project project = projectRepo.findById(resTaskDto.getProjectId()).orElseThrow(() -> new UsernameNotFoundException("Project not found"));

        if(!Objects.equals(user.getId(), project.getOwner().getId())){
            throw new AccessDeniedException("this doesn't belongs to you");
        }

        Task task = new Task();
        task.setTitle(resTaskDto.getTitle());
        task.setDescription(resTaskDto.getDescription());
        task.setCreatedAt(LocalDateTime.now());
        task.setDeadline(LocalDateTime.now().plusDays(1));
        task.setProject(project);
        task.setAssignee(user);
        task.setStatus(TaskStatus.IN_PROGRESS);


        taskRepo.save(task);

        return taskMapper.toDto(task);

    }


    public List<TaskDto> getAllTasks(String email) {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        return user.getAssignedTasks().stream().map(taskMapper::toDto).toList();
    }


    public TaskDto getTask(Long id, String email) throws AccessDeniedException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        Task task = taskRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("Project not found"));

        if(!Objects.equals(user.getId(), task.getAssignee().getId())){
            throw new AccessDeniedException("you don't have access");
        }

        return taskMapper.toDto(task);
    }

    public TaskDto updateTask(Long id, String email, ResTaskDto resTaskDto) throws AccessDeniedException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        Task task = taskRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("Project not found"));

        if(!Objects.equals(user.getId(), task.getAssignee().getId())){
            throw new AccessDeniedException("you don't have access");
        }

        if(StringUtils.hasText(resTaskDto.getTitle())){
            task.setTitle(resTaskDto.getTitle());
        }

        if(StringUtils.hasText(resTaskDto.getDescription())){
            task.setDescription(resTaskDto.getDescription());
        }

        taskRepo.save(task);

        return taskMapper.toDto(task);
    }
}
