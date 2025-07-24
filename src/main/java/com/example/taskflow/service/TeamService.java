package com.example.taskflow.service;

import com.example.taskflow.dto.NameAndEmailDto;
import com.example.taskflow.dto.ResTeamDto;
import com.example.taskflow.dto.ResUserIdDto;
import com.example.taskflow.dto.TeamDto;
import com.example.taskflow.entity.Team;
import com.example.taskflow.entity.User;
import com.example.taskflow.mapper.TeamMapper;
import com.example.taskflow.repository.TeamRepo;
import com.example.taskflow.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class TeamService {

    private final TeamRepo teamRepo;

    private final UserRepo userRepo;

    private final TeamMapper teamMapper;


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

    public List<TeamDto> getTeams(String email) {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        List<Team> teams = user.getTeams();
        return teams.stream().map(teamMapper::toDto).toList();
    }

    public TeamDto getTeamsById(Long id) {

        Team team = teamRepo.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));

        return teamMapper.toDto(team);
    }
}
