package com.sfcom.TicTacToeOnline.services;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.model.GameDto;

public interface GameManagerService {

    Game createGame(int userId, int friendId);

    Game findByUserId(int userId);

    void endForUser(int userId);

    GameDto toDto(Game game, Integer requester);

}
