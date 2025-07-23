package com.example.taskflow.entity;

import com.example.taskflow.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isVerified = false;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Task> assignedTasks = new ArrayList<>();

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Project> ownedProjects = new ArrayList<>();
}
