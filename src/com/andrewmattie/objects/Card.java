package com.andrewmattie.objects;

import javafx.scene.image.ImageView;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Card {
    private Player player;
    private int id;

    public Card(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    enum Suits {
        DIAMONDS,
        SPADES,
        CLUBS,
        HEARTS
    }

    enum Faces {
        JACK,
        TWO,
        TEN,
        QUEEN,
        KING,
        ACE,
        OTHER
    }

    public Suits checkSuit() {
        if (id >= 1 && id <= 13) {
            return Suits.SPADES;
        } else if (id >= 14 && id <= 26) {
            return Suits.HEARTS;
        } else if (id >= 27 && id <= 39) {
            return Suits.DIAMONDS;
        } else {
            return Suits.CLUBS;
        }
    }

    public Faces checkFace() {
        switch (id) {
            case 11:
            case 24:
            case 50:
            case 37:
                return Faces.JACK;
            case 2:
            case 15:
            case 28:
            case 41:
                return Faces.TWO;
            case 10:
            case 23:
            case 49:
            case 36:
                return Faces.TEN;
            case 12:
            case 38:
            case 51:
            case 25:
                return Faces.QUEEN;
            case 13:
            case 26:
            case 39:
            case 52:
                return Faces.KING;
            case 1:
            case 14:
            case 27:
            case 40:
                return Faces.ACE;
            default:
                return Faces.OTHER;
        }
    }

    public ImageView getFaceImage() {
        return new ImageView("assets/images/card/" + id + ".png");
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int card) {
        this.id = card;
    }
}
