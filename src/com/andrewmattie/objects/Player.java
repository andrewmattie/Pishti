package com.andrewmattie.objects;

import java.util.ArrayList;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Player {

    private ArrayList<Integer> playerCardsList;

    public Player(ArrayList<Integer> playerCardsList) {
        this.playerCardsList = playerCardsList;
    }

    public ArrayList<Integer> getPlayerCardsList() {
        return playerCardsList;
    }

    public void setPlayerCardsList(ArrayList<Integer> playerCardsList) {
        this.playerCardsList = playerCardsList;
    }
}
