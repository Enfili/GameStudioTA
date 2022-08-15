package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Country;

import java.util.List;

public interface CountryService {

    public List<Country> getCountries();

    public void addCountry(Country country);
}
