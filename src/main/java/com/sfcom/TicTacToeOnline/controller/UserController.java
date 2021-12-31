package com.sfcom.TicTacToeOnline.controller;

import com.sfcom.TicTacToeOnline.model.Game;
import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.services.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserManagerService userManager;

    @Autowired
    public UserController(UserManagerService userManager) {
        this.userManager = userManager;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Integer> create() {
        // maybe fix race condition in future?
        return ResponseEntity.ok(userManager.newUser());
    }

    @GetMapping(path = "/get-name/{userId}")
    public ResponseEntity<String> getName(@PathVariable int userId) {
        String name = userManager.getName(userId);
        if (isNull(name)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(name);
    }

    @GetMapping(path = "/add-friend/{friendId}")
    public String addFriend(@CookieValue(value = "userId", required=false) Integer userId, @PathVariable int friendId) {
        if (!userManager.exists(userId)) {
            return "redirect:/";
        }
        userManager.addFriend(userId, friendId);
        return "redirect:/menu/create";
    }

    @GetMapping(path = "/remove-friend/{friendId}")
    public String removeFriend(@CookieValue(value = "userId", required=false) Integer userId, @PathVariable int friendId) {
        if (!userManager.exists(userId)) {
            return "redirect:/";
        }
        userManager.removeFriend(userId, friendId);
        return "redirect:/menu/create";
    }

}
