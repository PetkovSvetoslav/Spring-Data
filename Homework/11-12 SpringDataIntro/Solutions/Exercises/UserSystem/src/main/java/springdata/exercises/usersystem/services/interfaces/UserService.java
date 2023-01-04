package springdata.exercises.usersystem.services.interfaces;

import springdata.exercises.usersystem.models.User;
import springdata.exercises.usersystem.models.gallery.Album;
import springdata.exercises.usersystem.models.location.Town;

import java.util.Date;
import java.util.List;

public interface UserService {
    void registerUser(User user);

    void registerUsers(Iterable<User> users);

    User findUser(long userId);

    void befriend(long first, long second);

    void addAlbumToUser(Album album, long userId);

    void setBornTownToUser(Town town, long userId);

    void addCurrentlyLivingTownToUser(Town town, long userId);

    List<String> findAllEmailsByProvider(String emailProvider);

    void markAsInactive(Date date);

    int deleteInactiveUsers();
}
