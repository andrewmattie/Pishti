package com.andrewmattie.objects;

import java.util.ArrayList;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Player {

    private ArrayList<Card> playerCardsList;
    private int score;

    public Player(ArrayList<Card> playerCardsList) {
        this.playerCardsList = playerCardsList;
    }

    public ArrayList<Card> getPlayerCardsList() {
        return playerCardsList;
    }

    public void setPlayerCardsList(ArrayList<Card> playerCardsList) {
        this.playerCardsList = playerCardsList;
    }

    public void removeCard(int index) {
        playerCardsList.remove(index);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

}
