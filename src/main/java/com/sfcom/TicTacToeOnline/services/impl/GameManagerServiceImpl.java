package com.sfcom.TicTacToeOnline.services.impl;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.model.GameDto;
import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.services.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
public class GameManagerServiceImpl implements GameManagerService {

    private final UserManagerService userManager;

    private Set<Game> games;
    private Map<Integer, Game> gamesByUserId = new HashMap<>();

    @Autowired
    public GameManagerServiceImpl(UserManagerService userManager) {
        this.userManager = userManager;
    }

    @Override
    public Game findByUserId(int userId) {
        return gamesByUserId.get(userId);
    }

    @Override
    public GameDto toDto(Game game, Integer requester) {
        if (isNull(game) || isNull(requester))
            return null;
        return new GameDto(requester, userManager.getName(game.player1Id), userManager.getName(game.player2Id), game);
    }

}