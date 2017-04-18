package com.andrewmattie.objects;

import java.util.ArrayList;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Player {

    private ArrayList<Integer> playerCardsList;
    private int score;

    public Player(ArrayList<Integer> playerCardsList) {
        this.playerCardsList = playerCardsList;
    }

    public ArrayList<Integer> getPlayerCardsList() {
        return playerCardsList;
    }

    public void setPlayerCardsList(ArrayList<Integer> playerCardsList) {
        this.playerCardsList = playerCardsList;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void subtractScore(int score) {
        this.score -= score;
    }
}
