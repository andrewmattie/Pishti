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
        int points = 0;
        if (pileArrayList.size() >= 2) {
            Player player = null;
            Card topCard = pileArrayList.get(pileArrayList.size() - 1);
            Card bottomCard = pileArrayList.get(pileArrayList.size() - 2);


            if (topCard.checkFace() == Card.Faces.JACK && bottomCard.checkFace() != Card.Faces.JACK) {
                System.out.println("win: 0");
                pileArrayList.clear();
                player = topCard.getPlayer();
                points = 1;
                System.out.println("CARD: " + topCard.getId());
            }

            //double
            else if (topCard.checkFace() == Card.Faces.JACK && bottomCard.checkFace() == Card.Faces.JACK
                    && pileArrayList.size() == 2) {
                System.out.println("win: 1");
                pileArrayList.clear();
                player = topCard.getPlayer();
                points = 10;
                System.out.println("CARD: " + topCard.getId());
            }

            //single
            else if (pileArrayList.size() == 2 && topCard.checkFace() == bottomCard.checkFace()
                    && topCard.checkFace() != Card.Faces.JACK) {
                System.out.println("win: 2");
                pileArrayList.clear();
                player = topCard.getPlayer();
                points = 10;
            }

            //other with switch
            else if (topCard.checkFace() == bottomCard.checkFace()) {
                pileArrayList.clear();
                player = topCard.getPlayer();
                System.out.println("win: 3");
                switch (topCard.checkFace()) {
                    case KING:
                    case QUEEN:
                    case JACK:
                    case ACE:
                        System.out.println("KQJA pointed");
                        System.out.println("CARD: " + topCard.getId());
                        points = 1;
                        break;
                    case TEN:
                        System.out.println("TEN pointed");
                        if (topCard.checkSuit() == Card.Suits.HEARTS) {
                            points = 3;
                        } else {
                            points = 1;
                        }
                        System.out.println("CARD: " + topCard.getId());
                        break;
                    case TWO:
                        System.out.println("TWO pointed");
                        if (topCard.checkSuit() == Card.Suits.SPADES) {
                            points = 2;
                        } else {
                            points = 3;
                        }
                        System.out.println("CARD: " + topCard.getId());
                        break;
                    case THREE:
                    case FOUR:
                    case FIVE:
                    case SIX:
                    case SEVEN:
                    case EIGHT:
                    case NINE:
                        System.out.println("GENERAL pointed");
                        System.out.println("CARD: " + topCard.getId());
                        points = 3;
                        break;
                    default:
//                        System.out.println("DEFAULT pointed");
//                        System.out.println("CARD: " + topCard.getId());
//                        points = 3;
                        points = 0;
                }
            }

            if (player != null) {
                System.out.println("ADDING SCORE: " + points);
                player.addScore(points);
                return player;
            }
        }

        return null;
    }

    public void addCardToPile(Card card) {
        pileArrayList.add(card);
    }

    public ArrayList<Card> getPileArrayList() {
        return pileArrayList;
    }
}
