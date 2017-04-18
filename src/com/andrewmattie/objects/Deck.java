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

    public Player checkForWin() {
        Player player = null;
        Card topCard = pileArrayList.get(pileArrayList.size());
        Card bottomCard = pileArrayList.get(pileArrayList.size() - 1);

        //todo run logic for pile win check
        // is win?
        if (topCard.checkFace() == bottomCard.checkFace()) {
            if (topCard.checkFace() == Card.Faces.JACK && bottomCard.checkFace() == Card.Faces.JACK) {
                pileArrayList.clear();
                player = topCard.getPlayer();
                player.addScore(20);
                return player;
            }

            int points = 0;
            for (Card card : pileArrayList) {
                switch (card.checkFace()) {
                    case KING:
                    case QUEEN:
                    case JACK:
                        points++;
                        break;
                    case TEN:
                        if (card.checkSuit() == Card.Suits.HEARTS) {
                            points += 3;
                        } else {
                            points++;
                        }
                        break;
                    case TWO:
                        if (card.checkSuit() == Card.Suits.SPADES) {
                            points += 2;
                        }
                        break;
                    default:
                        points += 3;
                }
            }

            pileArrayList.clear();
            player = topCard.getPlayer();
            player.addScore(points);
        }

        return player;
    }

    public void addCardToPile(Card card) {
        pileArrayList.add(card);
    }
}
