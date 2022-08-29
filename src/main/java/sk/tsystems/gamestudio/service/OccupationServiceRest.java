package sk.tsystems.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tsystems.gamestudio.entity.Occupation;

import java.util.Arrays;
import java.util.List;

public class OccupationServiceRest implements OccupationService{

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Occupation> getOccupations() {
        return Arrays.asList(restTemplate.getForEntity(url + "/occupation", Occupation[].class).getBody());
    }

    @Override
    public void addOccupation(Occupation occupation) {
        restTemplate.postForEntity(url + "/occupation", occupation, Occupation.class);
    }
}
