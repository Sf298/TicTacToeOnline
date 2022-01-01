package com.sfcom.TicTacToeOnline.services.impl;

import com.sfcom.TicTacToeOnline.model.PlayerListing;
import com.sfcom.TicTacToeOnline.services.UserManagerService;
import com.sfcom.TicTacToeOnline.utils.HeadersUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserManagerServiceImpl implements UserManagerService {

    private final Random rand = new Random();
    private final List<String> colours;
    private final List<String> animals;

    private final Map<Integer, String> idToName = new HashMap<>();
    private final Map<Integer, List<Integer>> friends = new HashMap<>();
    private final Map<String, List<Integer>> usersByIp = new HashMap<>();

    public UserManagerServiceImpl() throws IOException {
        colours = resourceToList("/lists/colours.txt");
        animals = resourceToList("/lists/animals.txt");
    }


    @Override
    public Integer newUser() {
        int newId = rand.nextInt();
        if (newId < 0) newId *= -1;
        String colour = colours.get(rand.nextInt(colours.size()));
        String animal = animals.get(rand.nextInt(animals.size()));

        idToName.put(newId, colour+" "+animal);

        return newId;
    }

    @Override
    public String getName(int id) {
        return idToName.get(id);
    }

    @Override
    public List<PlayerListing> getListings(List<Integer> ids, Integer requesterId) {
        return ids.stream()
                .filter(idToName::containsKey)
                .map(i -> new PlayerListing(i, idToName.get(i), getFriends(requesterId).contains(i)))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerListing> searchListings(String searchString, Integer requesterId) {
        List<String> tokens = List.of(searchString.trim().toLowerCase().split("[ ]+"));
        return idToName.entrySet().stream()
                .filter(e -> tokens.stream().anyMatch(t -> e.getValue().toLowerCase().contains(t)))
                .map(e -> new PlayerListing(e.getKey(), e.getValue(), getFriends(requesterId).contains(e.getKey())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean exists(Integer userId) {
        return nonNull(userId) && idToName.containsKey(userId);
    }


    @Override
    public void addFriend(int userId, int friendId) {
        List<Integer> list = friends.getOrDefault(userId, new ArrayList<>());
        list.add(friendId);
        friends.putIfAbsent(userId, list);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        if (!friends.containsKey(userId)) {
            return;
        }
        List<Integer> list = friends.get(userId);
        list.remove((Integer) friendId);
    }

    @Override
    public List<Integer> getFriends(int userId) {
        return Collections.unmodifiableList(friends.getOrDefault(userId, Collections.emptyList()));
    }


    @Override
    public void updateIp(Integer userId, HttpServletRequest request) {
        if (isNull(request)) return;
        updateIp(userId, HeadersUtils.getClientIp(request));
    }

    @Override
    public void updateIp(Integer userId, String ip) {
        if (isNull(userId)) {
            return;
        }

        if (usersByIp.containsKey(ip) && usersByIp.get(ip).contains(userId)) {
            return;
        }
        System.out.println("setting ip of '"+getName(userId)+" ("+userId+")' to "+ip);

        usersByIp.values().forEach(l -> l.remove(userId));

        List<Integer> list = usersByIp.getOrDefault(ip, new ArrayList<>());
        list.add(userId);
        usersByIp.putIfAbsent(ip, list);
    }

    @Override
    public List<Integer> findNearby(String ip) {
        return Collections.unmodifiableList(usersByIp.getOrDefault(ip, Collections.emptyList()));
    }

    @Override
    public List<Integer> findAll() {
        return usersByIp.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    private List<String> resourceToList(String classpath) throws IOException {
        List<String> list = new ArrayList<>();
        Resource resource = new ClassPathResource(classpath);
        Scanner in = new Scanner(resource.getInputStream());
        while (in.hasNextLine()) {
            list.add(in.nextLine());
        }
        return list;
    }

}
