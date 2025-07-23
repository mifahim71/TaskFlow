package com.example.taskflow.service;

import com.example.taskflow.dto.ProjectDto;
import com.example.taskflow.dto.ResProjectDto;
import com.example.taskflow.entity.Project;
import com.example.taskflow.entity.User;
import com.example.taskflow.mapper.ProjectMapper;
import com.example.taskflow.repository.ProjectRepo;
import com.example.taskflow.repository.UserRepo;
import jakarta.transaction.Transactional;
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
public class ProjectService {

    private final ProjectRepo projectRepo;

    private final UserRepo userRepo;

    private final ProjectMapper projectMapper;


    @Transactional
    public void createProject(ResProjectDto resProjectDto, String email) {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        Project project = new Project();

        project.setName(resProjectDto.getName());
        project.setDescription(resProjectDto.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setOwner(user);

        projectRepo.save(project);
    }


    public List<ProjectDto> getProjects(String email) {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));
        Long id = user.getId();

        List<Project> projects = projectRepo.findProjectsByUser(id);

        return projects.stream().map(projectMapper::toDto).toList();
    }

    public ProjectDto getProjectById(Long id, String email) throws AccessDeniedException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        Project project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project Not found"));

        if(!Objects.equals(user.getId(), project.getOwner().getId())){
            throw new AccessDeniedException("You don't own this project");
        }

        return projectMapper.toDto(project);

    }

    public ProjectDto updateProject(Long id, String email, ResProjectDto resProjectDto) throws AccessDeniedException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        Project project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project Not found"));

        if(!Objects.equals(user.getId(), project.getOwner().getId())){
            throw new AccessDeniedException("You don't own this project");
        }

        if(StringUtils.hasText(resProjectDto.getName())){
            project.setName(resProjectDto.getName());
        }

        if(StringUtils.hasText(resProjectDto.getDescription())){
            project.setDescription(resProjectDto.getDescription());
        }

        Project savedProject = projectRepo.save(project);

        return projectMapper.toDto(savedProject);

    }

    public void deleteProject(Long id, String email) throws AccessDeniedException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        Project project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project Not found"));

        if(!Objects.equals(user.getId(), project.getOwner().getId())){
            throw new AccessDeniedException("You don't own this project");
        }

        projectRepo.delete(project);
    }
}
