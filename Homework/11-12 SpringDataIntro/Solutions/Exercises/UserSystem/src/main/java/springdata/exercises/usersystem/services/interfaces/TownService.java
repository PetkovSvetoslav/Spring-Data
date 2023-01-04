package springdata.exercises.usersystem.services.interfaces;

import springdata.exercises.usersystem.models.location.Town;

public interface TownService {

    void registerTown(Town town);

    void registerTowns(Iterable<Town> towns);
}
