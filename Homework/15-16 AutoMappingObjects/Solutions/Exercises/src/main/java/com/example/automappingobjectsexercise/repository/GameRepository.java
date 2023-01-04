package com.example.automappingobjectsexercise.repository;

import com.example.automappingobjectsexercise.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByTitle(String title);
}
