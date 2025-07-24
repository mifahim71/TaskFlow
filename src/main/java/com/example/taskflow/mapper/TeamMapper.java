package com.example.taskflow.mapper;

import com.example.taskflow.dto.NameAndEmailDto;
import com.example.taskflow.dto.TeamDto;
import com.example.taskflow.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMapper {

    public TeamDto toDto(Team team){
        TeamDto teamDto = new TeamDto();

        teamDto.setName(team.getName());

        List<NameAndEmailDto> memberList = team.getMembers().stream().map(t -> {
            NameAndEmailDto nameAndEmailDto = new NameAndEmailDto();
            nameAndEmailDto.setName(t.getName());
            nameAndEmailDto.setEmail(t.getEmail());
            return nameAndEmailDto;
        }).toList();

        teamDto.setUserInfo(memberList);

        return teamDto;
    }
}
