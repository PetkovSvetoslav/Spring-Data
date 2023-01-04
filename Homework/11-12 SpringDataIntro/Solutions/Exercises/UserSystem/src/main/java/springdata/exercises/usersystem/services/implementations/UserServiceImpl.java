package springdata.exercises.usersystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.usersystem.exceptions.NonExistentUserException;
import springdata.exercises.usersystem.models.User;
import springdata.exercises.usersystem.models.gallery.Album;
import springdata.exercises.usersystem.models.location.Town;
import springdata.exercises.usersystem.repositories.UserRepository;
import springdata.exercises.usersystem.services.interfaces.AlbumService;
import springdata.exercises.usersystem.services.interfaces.TownService;
import springdata.exercises.usersystem.services.interfaces.UserService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AlbumService albumService;
    private final TownService townService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AlbumService albumService, TownService townService) {
        this.userRepository = userRepository;
        this.albumService = albumService;
        this.townService = townService;
    }

    @Override
    public void registerUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void registerUsers(Iterable<User> users) {
        this.userRepository.saveAll(users);
    }

    @Override
    public User findUser(long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException(userId));
    }

    @Override
    public void befriend(long firstId, long secondId) {
        if (firstId == secondId) {
            throw new IllegalArgumentException("You cannot make friends with yourself...");
        }

        User firstUser = this.findUser(firstId);
        User secondUser = this.findUser(secondId);

        firstUser.addFriend(secondUser);
        secondUser.addFriend(firstUser);
    }

    @Override
    public void addAlbumToUser(Album album, long userId) {
        User user = this.findUser(userId);

        this.albumService.registerAlbum(album);

        user.addAlbum(album);
    }

    @Override
    public void setBornTownToUser(Town town, long userId) {
        User user = this.findUser(userId);

        this.townService.registerTown(town);

        user.setBornTown(town);
    }

    @Override
    public void addCurrentlyLivingTownToUser(Town town, long userId) {
        User user = this.findUser(userId);

        this.townService.registerTown(town);

        user.setCurrentlyLiving(town);
    }

    @Override
    public List<String> findAllEmailsByProvider(String emailProvider) {
        return this.userRepository.findAllByEmailProvider(emailProvider)
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsInactive(Date date) {
        this.userRepository.setAllInactiveUsersAfterDateAsDeleted(date);
    }

    @Override
    public int deleteInactiveUsers() {
        return this.userRepository.deleteAllByIsDeletedTrue();
    }
}
