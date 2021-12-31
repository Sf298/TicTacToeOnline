package com.sfcom.TicTacToeOnline.controller;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.model.GameDto;
import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.services.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameManagerService gameManager;
    private final UserManagerService userManager;

    @Autowired
    public GameController(GameManagerService gameManager, UserManagerService userManager) {
        this.gameManager = gameManager;
        this.userManager = userManager;
    }

    @GetMapping
    public String getGamePage(@CookieValue(value = "userId", required=false) Integer userId, Model model) {
        if (!userManager.exists(userId)) {
            return "redirect:/";
        }

        Game game = gameManager.findByUserId(userId);
        GameDto gameDto = gameManager.toDto(game, userId);

        model.addAttribute("userId", userId);
        model.addAttribute("game", gameDto);
        return "game-page";
    }

    @GetMapping(path = "/create/{friendId}")
    public String createGame(@CookieValue(value = "userId", required=false) Integer userId, @PathVariable int friendId) {
        if (!userManager.exists(userId)) {
            return "redirect:/";
        }
        if (!userManager.exists(friendId)) {
            throw new IllegalArgumentException("Friend not found! id: " + friendId);
        }

        Game existingGame = gameManager.findByUserId(userId);
        if (nonNull(existingGame)) {
            if (existingGame.hasExactPlayers(userId, friendId)) {
                return "redirect:/game";
            } else {
                throw new IllegalStateException("Friend has a game already! id: " + friendId);
            }
        }

        gameManager.createGame(userId, friendId);

        // if already has game with clicked friend, redirect to it
        return "redirect:/game";
    }

    @GetMapping(path = "/state")
    public @ResponseBody GameDto getGameState(@CookieValue(value = "userId", required=false) Integer userId) {
        if (!userManager.exists(userId)) {
            return null;
        }
        Game game = gameManager.findByUserId(userId);
        return gameManager.toDto(game, userId);
    }

    @PostMapping(path = "/move/{position}")
    public ResponseEntity<String> makeMove(@CookieValue(value = "userId", required=false) Integer userId, @PathVariable int position) {
        if (!userManager.exists(userId)) {
            return null;
        }

        Game game = gameManager.findByUserId(userId);
        if (isNull(game)) {
            return ResponseEntity.status(404).body("Game not found");
        }

        try {
            game.play(userId, position);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(403).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("");
    }

    @GetMapping(path = "/end")
    public String endGame(@CookieValue(value = "userId", required=false) Integer userId, Model model) {
        if (!userManager.exists(userId)) {
            return "redirect:/";
        }

        Game game = gameManager.findByUserId(userId);
        if (isNull(game)) {
            return "redirect:/menu/create";
        }

        if (!game.isComplete()) {
            return "redirect:/game";
        }

        String state;
        if (isNull(game.winner())) {
            state = "It's a draw!";
        } else if (Objects.equals(game.winner(), userId)) {
            state = "You win!";
        } else {
            state = "You lose :(";

        }

        gameManager.endForUser(userId);

        model.addAttribute("state", state);
        return "end-page";
    }

    @DeleteMapping(path = "/end-saved")
    public String endSavedGame(@CookieValue(value = "userId", required=false) Integer userId) {
        if (userManager.exists(userId)) {
            gameManager.endForUser(userId);
        }

        return "redirect:/";
    }

}
