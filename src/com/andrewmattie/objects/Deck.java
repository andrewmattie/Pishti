package com.andrewmattie.objects;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Deck {

    private ArrayList<Integer> arrayList;

    public Deck(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
        shuffle();
    }

    public void shuffle() {
        arrayList.clear();

        for (int i = 1; i <= 54; i++) {
            arrayList.add(i);
        }

        Collections.shuffle(arrayList);
    }

    public ArrayList<Integer> dealCards() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(dealCard());
        }
        return list;
    }

    public int dealCard() {
        int card = arrayList.get(0);
        arrayList.remove(0);
        return card;
    }

    public ArrayList<Integer> getCardList() {
        return arrayList;
    }
}
