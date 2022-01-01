package com.sfcom.TicTacToeOnline.model;

import com.sfcom.TicTacToeOnline.enums.Player;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Game {

    public static int[][] WIN_STATES = new int[][] {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},

            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},

            {0, 4, 8},
            {2, 4, 6}
    };


    public long lastMove;
    public boolean forceQuit = false;

    public int player1Id;
    public int player2Id;
    public Integer turn;
    public Integer[] board;

    public Game(int player1Id, int player2Id) {
        this.lastMove = System.currentTimeMillis();

        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.turn = player1Id;
        this.board = new Integer[9];
    }

    public void play(int userId, int position) {
        if (turn != userId) {
            throw new IllegalStateException("Not this players turn!");
        }
        if (nonNull(board[position])) {
            throw new IllegalArgumentException("Cannot play this position: already taken!");
        }

        board[position] = userId;

        turn = turn==player1Id ? player2Id : player1Id;

        this.lastMove = System.currentTimeMillis();
    }

    public boolean isComplete() {
        return forceQuit || Arrays.stream(board).allMatch(Objects::nonNull) || nonNull(winner());
    }

    public void forceExit() {
        forceQuit = true;
    }

    public Integer winner() {
        outer: for (int[] winState : WIN_STATES) {
            Integer initial = board[winState[0]];
            if (isNull(initial)) {
                continue;
            }
            for (int i = 1; i < winState.length; i++) {
                if (!initial.equals(board[winState[i]])) {
                    continue outer;
                }
            }
            return initial;
        }

        return null;
    }

    public Player byUserId(int userId) {
        if (userId == player1Id) {
            return Player.PLAYER_1;
        } else if (userId == player2Id) {
            return Player.PLAYER_2;
        } else {
            throw new IllegalArgumentException("Player not found in this game");
        }
    }

    public Player byUserIdOrNull(Integer userId) {
        if (isNull(userId)) {
            return null;
        }
        if (userId == player1Id) {
            return Player.PLAYER_1;
        } else if (userId == player2Id) {
            return Player.PLAYER_2;
        } else {
            return null;
        }
    }

    public boolean hasPlayer(int playerId) {
        return playerId == player1Id || playerId == player2Id;
    }

    public boolean hasExactPlayers(int playerAId, int playerBId) {
        return (playerAId == player1Id && playerBId == player2Id) || (playerAId == player2Id && playerBId == player1Id);
    }

}
