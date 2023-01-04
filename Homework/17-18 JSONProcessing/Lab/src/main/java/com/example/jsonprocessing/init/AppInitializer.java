package com.example.jsonprocessing.init;

import com.example.jsonprocessing.entity.Post;
import com.example.jsonprocessing.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppInitializer implements CommandLineRunner {

    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Welcome to Spring Data", "Developing data access object with Spring Data is easy ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/rosa-klee-blute-blume.jpg",
                    "Angel"),
            new Post("Reactive Spring Data", "Check R2DBC for reactive JDBC API ...",
                    "https://www.publicdomainpictures.net/pictures/70000/velka/spring-grass-in-sun-light.jpg",
                    "Peter"),
            new Post("New in Spring 5", "Webflux provides reactive and non-blocking web service implemntation ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg",
                    "Milko"),
            new Post("Beginnig REST with Spring 5", "Spring MVC and WebFlux make implemeting RESTful services really easy ...",
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg",
                    "Teodor")
    );

    private final PostService postService;

    @Autowired
    public AppInitializer(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {
        SAMPLE_POSTS.forEach(this.postService::addPost);

        this.postService.getAllPosts();

        // Add post with HTTP client
        /*
        {
           "title": "GSON Post",
           "content": "GSON post example",
           "imageUrl": "https://www.publicdomainpictures.net/pictures/33000/somewhere/json-body.jpg",
           "author": "Misho"
        }
        */
    }
}
