package com.example.jsonprocessing.entity;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "posts")
public class Post {
    @Expose
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private Long id;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 3, max = 80)
    private String title;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 15, max = 2048)
    private String content;

    @Expose
    @NonNull
    @NotNull
    @URL
    private String imageUrl;

    @Expose
    @NonNull
    @NotNull
    private String author;

    @Expose
    private LocalDateTime created = LocalDateTime.now();

    @Expose
    private LocalDateTime modified = LocalDateTime.now();
}
