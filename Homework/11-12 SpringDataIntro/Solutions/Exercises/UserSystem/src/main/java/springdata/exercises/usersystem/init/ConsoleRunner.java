package springdata.exercises.usersystem.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.exercises.usersystem.exceptions.NonExistentUserException;
import springdata.exercises.usersystem.models.User;
import springdata.exercises.usersystem.models.gallery.Album;
import springdata.exercises.usersystem.models.gallery.Picture;
import springdata.exercises.usersystem.models.location.Country;
import springdata.exercises.usersystem.models.location.Town;
import springdata.exercises.usersystem.services.interfaces.AlbumService;
import springdata.exercises.usersystem.services.interfaces.UserService;

@Slf4j
@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AlbumService albumService;

    @Autowired
    public ConsoleRunner(UserService userService, AlbumService albumService) {
        this.userService = userService;
        this.albumService = albumService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {

            User user1 = new User("acho007", "12!Kdaw", "angel@kom.pot", (byte) 26,
                    "Angel", "Velkov");

            user1.setDeleted(true);

            this.userService.registerUser(user1);

            User user2 = new User(
                    "peter_veliki", "pat1Ad?an", "pepi-rabivacha21@tam.pon", (byte) 69,
                    "Peter", "Petrov");

            user2.setDeleted(true);

            this.userService.registerUser(user2);

            this.userService.befriend(1, 2);

            Album album = new Album("Screenshots", "blue", false);

            this.userService.addAlbumToUser(album, 1);

            Picture picture = new Picture("dog", "rex", "some/random/path");

            this.albumService.addPictureToAlbum(picture, 1);

            this.userService.setBornTownToUser(new Town("Sofia", new Country("Bulgaria")), 1);

            for (String host : this.userService.findAllEmailsByProvider("kom.pot")) {
                System.out.println(host);
            }

            this.albumService.setOwner(1, null);
            System.out.println(this.userService.deleteInactiveUsers());

        } catch (IllegalArgumentException | NonExistentUserException e) {
            log.error(e.getMessage());
        }
    }
}
