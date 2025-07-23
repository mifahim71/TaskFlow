package com.example.taskflow.mapper;

import com.example.taskflow.dto.ProjectDto;
import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.entity.Project;
import com.example.taskflow.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
    public TaskDto toDto(Task task){

        TaskDto taskDto = new TaskDto();

        taskDto.setTitle(task.getTitle());

        taskDto.setDescription(task.getDescription());

        taskDto.setOwnerName(task.getAssignee().getName());

        taskDto.setProjectName(task.getProject().getName());

        taskDto.setDeadLine(task.getDeadline());

        taskDto.setStatus(task.getStatus().toString());

        return taskDto;
    }
}
