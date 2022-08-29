package sk.tsystems.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tsystems.gamestudio.entity.Country;

import java.util.Arrays;
import java.util.List;

public class CountryServiceRest implements CountryService{

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Country> getCountries() {
        return Arrays.asList(restTemplate.getForEntity(url + "/country", Country[].class).getBody());
    }

    @Override
    public void addCountry(Country country) {
        restTemplate.postForEntity(url + "/country", country, Country.class);
    }
}
