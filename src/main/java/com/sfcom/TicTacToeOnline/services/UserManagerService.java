package com.sfcom.TicTacToeOnline.services;

import com.sfcom.TicTacToeOnline.model.PlayerListing;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserManagerService {

    Integer newUser();

    String getName(int id);

    List<PlayerListing> getListings(List<Integer> ids, Integer requesterId);

    List<PlayerListing> searchListings(String searchString, Integer requesterId);

    boolean exists(Integer userId);

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    List<Integer> getFriends(int userId);

    void updateIp(Integer userId, HttpServletRequest request);

    void updateIp(Integer userId, String ip);

    List<Integer> findNearby(String ip);

    List<Integer> findAll();
}
