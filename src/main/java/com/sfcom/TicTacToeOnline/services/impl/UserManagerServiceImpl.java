package com.sfcom.TicTacToeOnline.services.impl;

import com.sfcom.TicTacToeOnline.services.UserManagerService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserManagerServiceImpl implements UserManagerService {

    private final Random rand = new Random();
    private final List<String> colours;
    private final List<String> animals;

    private final Map<Integer, String> idToName = new HashMap<>();

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
