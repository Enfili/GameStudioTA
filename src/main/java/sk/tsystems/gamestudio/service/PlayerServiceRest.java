package sk.tsystems.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tsystems.gamestudio.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerServiceRest implements PlayerService{

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Player> getPlayersByUserName(String uName) {
        return Arrays.asList(restTemplate.getForEntity(url + "/player" + "/uName", Player[].class).getBody());
    }

    @Override
    public void addPlayer(Player player) {
        restTemplate.postForObject(url + "/player", player, Player.class);
    }

    @Override
    public List<Player> getPlayers() {
        return Arrays.asList(restTemplate.getForObject(url + "/player/all", Player[].class));
    }
}
