package com.sfcom.TicTacToeOnline.controller;

import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.services.UserManagerService;
import com.sfcom.TicTacToeOnline.utils.HeadersUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping
public class MenuController {

    private final GameManagerService gameManager;
    private final UserManagerService userManager;

    @Autowired
    public MenuController(GameManagerService gameManager, UserManagerService userManager) {
        this.gameManager = gameManager;
        this.userManager = userManager;
    }

    @GetMapping(value = "/")
    public String index(@CookieValue(value = "userId", required=false) Integer userId,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        if (!userManager.exists(userId)) {
            userId = userManager.newUser();
            response.addCookie(new Cookie("userId", String.valueOf(userId)));
            userManager.updateIp(userId, HeadersUtils.getClientIp(request));
        }

        boolean hasGame = nonNull(gameManager.findByUserId(userId));
        String userName = userManager.getName(userId);

        model.addAttribute("hasGame", hasGame);
        model.addAttribute("userName", userName);

        return "index";
    }

    @GetMapping(value = "/menu/create")
    public String createGameMenu(@CookieValue(value = "userId", required=false) Integer userId,
                                 HttpServletRequest request,
                                 Model model) {
        if (!userManager.exists(userId)) {
            return "redirect:/";
        }

        String userName = userManager.getName(userId);
        List<Integer> friendIds = userManager.getFriends(userId);
        Map<String, String> friends = userManager.getNames(friendIds).entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));

        // TODO Grey out unjoinable players

        String userIp = HeadersUtils.getClientIp(request);
        List<Integer> nearbyIds = userManager.findAll();
        Map<String, String> nearby = userManager.getNames(nearbyIds).entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
        friends.keySet().forEach(nearby::remove);
        nearby.remove(userId+"");

        // TODO add new section for searching global players

        model.addAttribute("userName", userName);
        model.addAttribute("friends", friends);
        model.addAttribute("nearby", nearby);
        return "player-selector";
    }

}
