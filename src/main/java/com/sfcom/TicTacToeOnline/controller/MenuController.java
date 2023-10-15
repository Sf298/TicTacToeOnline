package com.sfcom.TicTacToeOnline.controller;

import com.sfcom.TicTacToeOnline.model.PlayerListing;
import com.sfcom.TicTacToeOnline.services.GameManagerService;
import com.sfcom.TicTacToeOnline.services.UserManagerService;
import com.sfcom.TicTacToeOnline.utils.HeadersUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        List<PlayerListing> friends = userManager.getListings(friendIds, userId);
        gameManager.populateAvailability(friends, userId);

        // TODO slight gap missing between search results name and friend buttons
        // TODO add IP address expiry

        Set<Integer> idsToExclude = new HashSet<>(friendIds);
        idsToExclude.add(userId);

        String userIp = HeadersUtils.getClientIp(request);
        List<Integer> nearbyIds = userManager.findNearby(userIp);
        List<PlayerListing> nearby = userManager.getListings(nearbyIds, userId).stream()
                .filter(l -> !idsToExclude.contains(l.id))
                .collect(Collectors.toList());
        gameManager.populateAvailability(nearby, userId);

        model.addAttribute("userName", userName);
        model.addAttribute("friends", friends);
        model.addAttribute("nearby", nearby);
        return "player-selector";
    }

}
