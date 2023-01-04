package com.example.jsonprocessing.service.impl;

import com.example.jsonprocessing.entity.Post;
import com.example.jsonprocessing.exception.NonExistingEntityException;
import com.example.jsonprocessing.repository.PostRepository;
import com.example.jsonprocessing.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public Post getPostById(long id) {
        return this.postRepository.findById(id).orElseThrow(() ->
                new NonExistingEntityException(
                        "Post with ID=" + id + " does not exist."
                )
        );
    }

    @Transactional
    @Override
    public Post addPost(Post post) {
        post.setId(null);
        return this.postRepository.save(post);
    }

    @Transactional
    @Override
    public Post updatePost(Post post) {
        this.getPostById(post.getId());
        return this.postRepository.save(post);
    }

    @Transactional
    @Override
    public boolean deletePost(long id) {
        return this.deletePost(id);
    }

    @Override
    public long getPostsCount() {
        return this.postRepository.count();
    }
}
