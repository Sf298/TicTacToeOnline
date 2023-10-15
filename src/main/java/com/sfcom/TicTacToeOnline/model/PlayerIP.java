package com.sfcom.TicTacToeOnline.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerIP {

    private String ipAddress;
    private int userId;
    private long lastUpdated;

    public PlayerIP(int userId) {
        this.userId = userId;
    }

    public long age() {
        return System.currentTimeMillis() - lastUpdated;
    }

}
