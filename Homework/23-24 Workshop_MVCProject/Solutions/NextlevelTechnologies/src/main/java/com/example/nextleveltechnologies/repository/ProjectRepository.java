package com.example.nextleveltechnologies.repository;

import com.example.nextleveltechnologies.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsAllBy();

    Optional<Project> findByName(String name);

    List<Project> findAllByIsFinishedIsTrue();
}
