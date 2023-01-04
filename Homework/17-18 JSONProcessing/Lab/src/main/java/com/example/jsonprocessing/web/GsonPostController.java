package com.example.jsonprocessing.web;

import com.example.jsonprocessing.entity.Post;
import com.example.jsonprocessing.service.PostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/gson/posts")
public class GsonPostController {

    private final PostService postService;
    private final Gson gson;

    @Autowired
    public GsonPostController(PostService postService) {
        this.postService = postService;
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @GetMapping(produces = "application/json")
    public String getPosts() {
        return this.gson.toJson(this.postService.getAllPosts());
    }

    @GetMapping(path = "/{id}",
     produces = "application/json")
    public String getPost(@PathVariable(name = "id") long id) {
        return this.gson.toJson(this.postService.getPostById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> addPost(@RequestBody String body) {
        log.info("Body received: {}", body);
        Post post = gson.fromJson(body, Post.class);
        log.info("Post: {}", post);

        Post created = this.postService.addPost(post);

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .pathSegment("{id}")
                                .buildAndExpand(created.getId().toString())
                                .toUri()
                ).body(gson.toJson(created));
    }
}
