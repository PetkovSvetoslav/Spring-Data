package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.time.LocalDate;

@Entity(name = "accounts")
public class Account {
    @Id
    @Column(name = "id", columnDefinition = "")
    private long id;

    @Column(name = "name", columnDefinition = "")
    private String name;


    @Column(name = "age", columnDefinition = "")
    private Integer age;

    @Column(name = "createdOn", columnDefinition = "")
    private LocalDate createdOn;

    @Column(name = "nickname", columnDefinition = "")
    private String nickname;

    public Account() {
    }

    public Account(String name, LocalDate createdOn, Integer age) {
        this.name = name;
        this.createdOn = createdOn;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
