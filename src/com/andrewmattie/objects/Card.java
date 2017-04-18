package com.andrewmattie.objects;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Card {

    enum SUITS {
        DIAMONDS,
        SPADES,
        CLUBS,
        HEARTS
    }

    enum FACES {
        JACK,
        TWO,
        TEN,
        QUEEN,
        KING
    }

    public SUITS checkSuit(int card) {
        if (card >= 1 && card <= 13) {
            return SUITS.SPADES;
        } else if (card >= 14 && card <= 26) {
            return SUITS.HEARTS;
        } else if (card >= 27 && card <= 39) {
            return SUITS.DIAMONDS;
        } else {
            return SUITS.CLUBS;
        }
    }

    public FACES checkFace(int card) {
        switch (card) {
            case 11:
            case 24:
            case 50:
            case 37:
                return FACES.JACK;
            case 2:
            case 15:
            case 28:
            case 41:
                return FACES.TWO;
            case 10:
            case 23:
            case 49:
            case 36:
                return FACES.TEN;
            case 12:
            case 38:
            case 51:
            case 25:
                return FACES.QUEEN;
            case 13:
            case 26:
            case 39:
            case 52:
                return FACES.KING;
            default:
                return null;
        }
    }
}
