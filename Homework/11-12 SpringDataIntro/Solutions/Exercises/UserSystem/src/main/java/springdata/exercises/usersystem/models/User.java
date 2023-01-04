package springdata.exercises.usersystem.models;

import springdata.exercises.usersystem.exceptions.InvalidEmailException;
import springdata.exercises.usersystem.exceptions.InvalidPasswordException;
import springdata.exercises.usersystem.models.location.Town;
import springdata.exercises.usersystem.models.gallery.Album;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String username;
    private String password;
    private String email;
    private Date registeredOn;
    private Date lastTimeLoggedIn;
    private byte age;
    private boolean isDeleted;

    private Town bornTown;
    private Town currentlyLiving;

    private String firstName;
    private String lastName;

    private Set<User> friends;

    private Set<Album> albums;

    public User() {
    }

    public User(String username, String password, String email,
                byte age, String firstName, String lastName) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setAge(age);

        this.registeredOn = new Date();

        this.firstName = firstName;
        this.lastName = lastName;

        this.friends = new HashSet<>();

        this.albums = new HashSet<>();
    }

    public User(String username, String password, String email, byte age,
                String firstName, String lastName, Town bornTown, Town currentlyLiving) {
        this(username, password, email, age, firstName, lastName);

        this.bornTown = bornTown;
        this.currentlyLiving = currentlyLiving;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.validateUsername(username);

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.validatePassword(password);

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.validateEmail(email);

        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Date getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(Date lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.validateAge(age);

        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Town getCurrentlyLiving() {
        return currentlyLiving;
    }

    public void setCurrentlyLiving(Town currentlyLiving) {
        this.currentlyLiving = currentlyLiving;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album) {
        if (album.getOwner() != null) {
            throw new IllegalStateException("The album already has an owner!");
        }

        this.albums.add(album);
        album.setOwner(this);
    }

    private void validateUsername(String username) {
        this.ensureNonNull(username, "username");

        if (4 > username.length() || username.length() > 30) {
            throw new IllegalArgumentException("The username length needs to be between 4 and 30 symbols.");
        }
    }

    private void validatePassword(String password) {
        this.ensureNonNull(password, "password");

        String regex = "^(?=.*[0-9])" +        // Whether it contains at least one digit
                "(?=.*[a-z])" +                // Whether it contains at least one lowercase letter
                "(?=.*[A-Z])" +                // Whether it contains at least one uppercase letter
                "(?=.*[!#$%^&*()_+<>?])" +     // Whether it contains at least one of the symbols
                "(?=\\S+$)" +                  // It doesn't contain any white space
                ".{6,50}$";                    // With a length between 6 and 50 characters

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new InvalidPasswordException();
        }
    }

    private void validateEmail(String email) {
        this.ensureNonNull(email, "email");

        String regex = "^(?<user>[a-zA-Z0-9]+([.\\-_]*[a-zA-Z0-9])+)@" +
                "(?<host>[a-zA-Z]+([.-]?[a-zA-Z])\\.[a-zA-Z]+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.find()) {
            throw new InvalidEmailException();
        }
    }

    private void validateAge(byte age) {
        if (1 > age || age > 120) {
            throw new IllegalArgumentException("The age must be between [1, 120]");
        }
    }

    private void ensureNonNull(Object o, String entityType) {
        if (o == null) {
            throw new IllegalArgumentException("The " + entityType + " cannot be null!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
