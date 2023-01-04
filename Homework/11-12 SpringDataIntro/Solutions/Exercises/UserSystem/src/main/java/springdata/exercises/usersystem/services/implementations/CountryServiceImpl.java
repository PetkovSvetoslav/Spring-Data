package springdata.exercises.usersystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.exercises.usersystem.models.location.Country;
import springdata.exercises.usersystem.repositories.CountryRepository;
import springdata.exercises.usersystem.services.interfaces.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void registerCountry(Country country) {
        this.countryRepository.save(country);
    }

    @Override
    public void registerCountries(Iterable<Country> countries) {
        this.countryRepository.saveAll(countries);
    }
}
