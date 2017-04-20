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

    public boolean pickCard(Card faceCard) {
        for (int i = 0; i < arrayList.size(); i++) {
            Card card = arrayList.get(i);

            if (card.checkFace() == Card.Faces.JACK) {
                //play jack
                return true;
            }
        }

        for (int i = 0; i < arrayList.size(); i++) {
            Card card = arrayList.get(i);

            if (card.checkFace() == faceCard.checkFace()) {
                //play card
                return true;
            }
        }

        return false;
    }
}
