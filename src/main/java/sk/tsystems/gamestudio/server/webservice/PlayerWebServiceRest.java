package sk.tsystems.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.PlayerService;
import sk.tsystems.gamestudio.service.PlayerServiceRest;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerWebServiceRest {

    @Autowired
    PlayerService playerService;

    @GetMapping("/{uName}")
    public List<Player> getPlayersByUserName(@PathVariable String uName) {
        return playerService.getPlayersByUserName(uName);
    }

    @PostMapping
    public void addPlayer(@RequestBody Player player) {
        playerService.addPlayer(player);
    }

    @GetMapping("/all")
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }
}
