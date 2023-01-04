package springdata.exercises.usersystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.usersystem.models.gallery.Picture;
import springdata.exercises.usersystem.repositories.PictureRepository;
import springdata.exercises.usersystem.services.interfaces.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void registerPicture(Picture picture) {
        this.pictureRepository.save(picture);
    }
}
