package com.sfcom.TicTacToeOnline.controller;

import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.utils.HeadersUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping
public class MenuController {

    private final GameManagerService gameManager;

    @Autowired
    public MenuController(GameManagerService gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping(value = "/")
    public String index(@RequestHeader HttpHeaders headers, Model model) {
        Integer userId = HeadersUtils.getUserId(headers);

        boolean hasGame = nonNull(userId) && nonNull(gameManager.findByUserId(userId));
        model.addAttribute("hasGame", hasGame);

        return "index";
    }

}
