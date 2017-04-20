package com.andrewmattie.objects;

import java.util.ArrayList;

/**
 * Created by andrewmattie on 4/20/17.
 */
public class Ai {
    private ArrayList<Card> arrayList;

    public Ai(ArrayList<Card> arrayList) {
        this.arrayList = arrayList;
    }

    public int pickCard(Deck deck) {
        for (int i = 0; i < arrayList.size(); i++) {
            Card card = arrayList.get(i);

            if (card.checkFace() == Card.Faces.JACK) {
                //play jack
                return i;
            }
        }

        for (int i = 0; i < arrayList.size(); i++) {
            Card card = arrayList.get(i);

            if (card.checkFace() == deck.getPileArrayList().get(deck.getPileArrayList().size() - 1).checkFace()) {
                //play card
                return i;
            }
        }

        return -1;
    }
}
