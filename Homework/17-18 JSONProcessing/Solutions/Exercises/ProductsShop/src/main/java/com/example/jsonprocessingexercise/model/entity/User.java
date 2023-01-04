package com.example.jsonprocessingexercise.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

    private byte age;

    @ToString.Exclude
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private Set<Product> products;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "users_friends",
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    private void addFriend(User user) {
        this.friends.add(user);
        user.friends.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
