package com.example.taskflow.repository;

import com.example.taskflow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM project WHERE owner_id = :id", nativeQuery = true)
    List<Project> findProjectsByUser(@Param("id") Long id);
}
