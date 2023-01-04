package com.example.automappingobjectsexercise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String trailer;
    @NonNull
    private String imageThumbnail;
    @NonNull
    private Double size;
    @NonNull
    @Column(precision = 9, scale = 2)
    private BigDecimal price;
    @NonNull
    @Column(columnDefinition = "TEXT")
    protected String description;
    @NonNull
    private LocalDate releaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id)
                && title.equals(game.title)
                && trailer.equals(game.trailer)
                && imageThumbnail.equals(game.imageThumbnail)
                && size.equals(game.size)
                && price.equals(game.price)
                && description.equals(game.description)
                && releaseDate.equals(game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, trailer, imageThumbnail, size, price, description, releaseDate);
    }
}
