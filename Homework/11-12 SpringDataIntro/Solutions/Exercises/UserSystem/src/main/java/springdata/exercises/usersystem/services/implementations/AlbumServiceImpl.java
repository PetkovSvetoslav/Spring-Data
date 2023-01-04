package springdata.exercises.usersystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.usersystem.exceptions.NonExistentAlbumException;
import springdata.exercises.usersystem.models.User;
import springdata.exercises.usersystem.models.gallery.Album;
import springdata.exercises.usersystem.models.gallery.Picture;
import springdata.exercises.usersystem.repositories.AlbumRepository;
import springdata.exercises.usersystem.services.interfaces.AlbumService;
import springdata.exercises.usersystem.services.interfaces.PictureService;

import javax.transaction.Transactional;

@Transactional
@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final PictureService pictureService;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, PictureService pictureService) {
        this.albumRepository = albumRepository;
        this.pictureService = pictureService;
    }

    @Override
    public void registerAlbum(Album album) {
        this.albumRepository.save(album);
    }

    @Override
    public void addPictureToAlbum(Picture picture, long albumId) {
        Album album = this.albumRepository.findById(albumId).orElseThrow(() -> new NonExistentAlbumException(albumId));

        this.pictureService.registerPicture(picture);

        album.addPicture(picture);
    }

    @Override
    public void setOwner(long albumId, User user) {
        Album album = this.albumRepository.findById(albumId).orElseThrow();
        album.setOwner(user);
    }
}
