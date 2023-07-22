package com.example.gameserver.manager;

import com.example.gameserver.games.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameItemManger {

    private static final Map<UUID, Game> games = new HashMap<UUID, Game>();

    public static void addGame(Game game){
        games.put(game.getId(), game);
    }

    public static Map<UUID, Game> getGames(){
        return games;
    }

}
