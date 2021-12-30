package com.sfcom.TicTacToeOnline.services;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.model.GameDto;

public interface GameManagerService {

    Game findByUserId(int userId);

    GameDto toDto(Game game, Integer requester);

}
