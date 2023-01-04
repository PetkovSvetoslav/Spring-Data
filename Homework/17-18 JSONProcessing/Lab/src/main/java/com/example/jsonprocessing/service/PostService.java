package com.example.jsonprocessing.service;

import com.example.jsonprocessing.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPostById(long id);

    Post addPost(Post post);

    Post updatePost(Post post);

    boolean deletePost(long id);

    long getPostsCount();
}
