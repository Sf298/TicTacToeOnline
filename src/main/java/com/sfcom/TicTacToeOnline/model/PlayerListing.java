package com.sfcom.TicTacToeOnline.model;

import lombok.Data;

@Data
public class PlayerListing {

    public int id;
    public String name;
    public boolean available;
    public boolean friend;

    public PlayerListing(int id, String name) {
        this(id, name, false);
    }

    public PlayerListing(int id, String name, boolean isFriend) {
        this.id = id;
        this.name = name;
        this.friend = isFriend;
    }
}
