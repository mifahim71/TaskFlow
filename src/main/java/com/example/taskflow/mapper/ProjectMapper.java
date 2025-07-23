package com.example.taskflow.mapper;

import com.example.taskflow.dto.ProjectDto;
import com.example.taskflow.entity.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {

    public ProjectDto toDto(Project project){

        ProjectDto projectDto = new ProjectDto();

        projectDto.setName(project.getName());

        projectDto.setDescription(project.getDescription());

        projectDto.setOwnerName(project.getOwner().getName());

        projectDto.setCreatedAt(project.getCreatedAt());

        return projectDto;
    }
}
