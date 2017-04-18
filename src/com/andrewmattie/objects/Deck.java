package com.andrewmattie.objects;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andrewmattie on 4/17/17.
 */
public class Deck {

    private ArrayList<Card> masterCardArrayList;
    private ArrayList<Card> pileArrayList;

    public Deck() {
        masterCardArrayList = new ArrayList<>();
        pileArrayList = new ArrayList<>();
        shuffle();
    }

    public void shuffle() {
        masterCardArrayList.clear();

        for (int i = 1; i <= 52; i++) {
            masterCardArrayList.add(new Card(null, i));
        }

        Collections.shuffle(masterCardArrayList);
    }

    public ArrayList<Card> dealCards(Player player) {
        ArrayList<Card> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(dealCard(player));
        }
        return list;
    }

    public Card dealCard(Player player) {
        Card card = masterCardArrayList.get(0);
        card.setPlayer(player);
        masterCardArrayList.remove(0);
        masterCardArrayList.trimToSize();
        return card;
    }

    public ArrayList<Card> getCardList() {
        return masterCardArrayList;
    }

    public boolean checkForWin() {
        boolean win = false;
        Card topCard = pileArrayList.get(pileArrayList.size());
        Card bottomCard = pileArrayList.get(pileArrayList.size() - 1);

        //todo run logic for pile win check
        // is win?
        if (topCard.checkFace() == bottomCard.checkFace()) {
            //todo add up points before clear
            pileArrayList.clear();
            win = true;
        }

        return win;
    }

    public void addCardToPile(Card card) {
        pileArrayList.add(card);
    }
}
