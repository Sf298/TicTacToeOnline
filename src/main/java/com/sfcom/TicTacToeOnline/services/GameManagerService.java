package com.sfcom.TicTacToeOnline.services;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.model.GameDto;
import com.sfcom.TicTacToeOnline.model.PlayerListing;

import java.util.List;

public interface GameManagerService {

    Game createGame(int userId, int friendId);

    Game findByUserId(int userId);

    void endForUser(int userId);

    void populateAvailability(List<PlayerListing> listings, Integer requesterId);

    GameDto toDto(Game game, Integer requester);

}
