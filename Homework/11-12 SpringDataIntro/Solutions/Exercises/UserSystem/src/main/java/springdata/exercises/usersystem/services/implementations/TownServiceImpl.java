package springdata.exercises.usersystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.usersystem.models.location.Town;
import springdata.exercises.usersystem.repositories.TownRepository;
import springdata.exercises.usersystem.services.interfaces.TownService;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public void registerTown(Town town) {
        this.townRepository.save(town);
    }

    @Override
    public void registerTowns(Iterable<Town> towns) {
        this.townRepository.saveAll(towns);
    }
}
