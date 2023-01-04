package com.example.jsonprocessing.repository;

import com.example.jsonprocessing.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
