package com.example.taskflow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String title;

    private String description;

    private String ownerName;

    private String projectName;

    @JsonFormat(pattern = "YYYY:MM:DD | hh:mm:ss")
    private LocalDateTime deadLine;

    private String status;
}
