package com.sfcom.TicTacToeOnline.model;

import com.sfcom.TicTacToeOnline.enums.Player;

import java.util.Arrays;

import static java.util.Objects.nonNull;

public class GameDto {

    public int requester;
    public Player requesterPlayer;

    public Player turn; // game over if null
    public Player winner;

    public String player1Name;
    public String player2Name;
    public Player[] board;

    public GameDto(int requester, String player1Name, String player2Name, Game game) {
        this.requester = requester;
        this.requesterPlayer = game.byUserId(requester);

        this.turn = game.byUserIdOrNull(game.turn);
        this.winner = game.byUserIdOrNull(game.winner());
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.board = Arrays.stream(game.board).map(game::byUserIdOrNull).toArray(Player[]::new);
    }
}
