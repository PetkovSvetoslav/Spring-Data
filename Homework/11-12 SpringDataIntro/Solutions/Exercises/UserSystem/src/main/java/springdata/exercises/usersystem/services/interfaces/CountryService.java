package springdata.exercises.usersystem.services.interfaces;

import springdata.exercises.usersystem.models.location.Country;

public interface CountryService {
    void registerCountry(Country country);

    void registerCountries(Iterable<Country> countries);
}
