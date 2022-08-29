package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Player;

import java.util.List;

public interface PlayerService {

    public List<Player> getPlayersByUserName(String uName);

    public void addPlayer(Player player);

    public List<Player> getPlayers();
}
