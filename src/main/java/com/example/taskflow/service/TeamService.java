package com.example.taskflow.service;

import com.example.taskflow.dto.NameAndEmailDto;
import com.example.taskflow.dto.ResTeamDto;
import com.example.taskflow.dto.ResUserIdDto;
import com.example.taskflow.dto.TeamDto;
import com.example.taskflow.entity.Team;
import com.example.taskflow.entity.User;
import com.example.taskflow.repository.TeamRepo;
import com.example.taskflow.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class TeamService {

    private final TeamRepo teamRepo;

    private final UserRepo userRepo;


    public Team createTeam(ResTeamDto resTeamDto) {

        Team team = new Team();

        team.setName(resTeamDto.getName());
        team.setCreatedAt(LocalDateTime.now());

        return teamRepo.save(team);
    }

    public TeamDto addUserToTeam(Long id, ResUserIdDto resUserIdDto) {

        Team team = teamRepo.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));

        User user = userRepo.findById(resUserIdDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        team.getMembers().add(user);

        teamRepo.save(team);

        TeamDto teamDto = new TeamDto();
        NameAndEmailDto nameAndEmailDto = new NameAndEmailDto();

        nameAndEmailDto.setName(user.getName());
        nameAndEmailDto.setEmail(user.getEmail());

        teamDto.setName(team.getName());
        teamDto.getUserInfo().add(nameAndEmailDto);

        return teamDto;
    }
}
