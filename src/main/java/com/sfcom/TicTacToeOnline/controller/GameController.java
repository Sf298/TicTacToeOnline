package com.sfcom.TicTacToeOnline.controller;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.model.GameDto;
import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.utils.HeadersUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameManagerService gameManager;

    @Autowired
    public GameController(GameManagerService gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping
    public String getGamePage(@RequestHeader HttpHeaders headers, Model model) {
        Integer userId = HeadersUtils.getUserId(headers);
        Game game = nonNull(userId) ? gameManager.findByUserId(userId) : null;
        GameDto gameDto = gameManager.toDto(game, userId);

        model.addAttribute("userId", userId);
        model.addAttribute("game", gameDto);
        return "game-page";
    }

    @GetMapping(path = "/state/{userId}")
    public @ResponseBody GameDto getGameState(@PathVariable int userId) {
        Game game = gameManager.findByUserId(userId);
        return gameManager.toDto(game, userId);
    }

    @PostMapping(path = "/move/{userId}/{position}")
    public ResponseEntity<?> makeMove(@PathVariable int userId, @PathVariable int position) {
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

        return ResponseEntity.ok(gameManager.toDto(game, userId));
    }

}
