package com.sfcom.TicTacToeOnline.services;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserManagerService {

    Integer newUser();

    String getName(int id);

    Map<Integer, String> getNames(List<Integer> ids);

    boolean exists(Integer userId);

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    List<Integer> getFriends(int userId);

    void updateIp(Integer userId, HttpServletRequest request);

    void updateIp(Integer userId, String ip);

    List<Integer> findNearby(String ip);
}
