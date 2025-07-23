package com.example.taskflow.controller;

import com.example.taskflow.dto.ResTeamDto;
import com.example.taskflow.dto.ResUserIdDto;
import com.example.taskflow.dto.TeamDto;
import com.example.taskflow.entity.Team;
import com.example.taskflow.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Team> createTeam(@RequestBody ResTeamDto resTeamDto){
        try{
            Team team = teamService.createTeam(resTeamDto);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/add-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDto> addMember(@PathVariable Long id, @RequestBody ResUserIdDto resUserIdDto){
        try {
            TeamDto teamDto = teamService.addUserToTeam(id, resUserIdDto);
            return ResponseEntity.ok(teamDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
