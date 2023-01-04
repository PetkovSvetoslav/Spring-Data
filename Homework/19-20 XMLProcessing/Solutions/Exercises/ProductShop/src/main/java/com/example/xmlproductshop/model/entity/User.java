package com.example.xmlproductshop.model.entity;

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
public class User extends BaseEntity {

    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

    private byte age;

    @ToString.Exclude
    @OneToMany(mappedBy = "seller")
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
}
