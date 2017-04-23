package com.andrewmattie;

import com.andrewmattie.objects.Ai;
import com.andrewmattie.objects.Card;
import com.andrewmattie.objects.Deck;
import com.andrewmattie.objects.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;


public class Main extends Application {
    private Stage primaryStage;
    private BorderPane borderPane;
    private Scene scene;
    private HBox playerHandHBox;
    private HBox botHandHBox;
    private VBox rightBottomInfoVBox;
    private VBox rightTopInfoVBox;
    private VBox rightContainerVBox;
    private GridPane deckPane;
    private HBox deckHBox;
    private VBox deckVBox;
    private VBox logoVBox;
    private Player player;
    private Player botPlayer;
    private Deck deck;
    private Label playerScoreLabel;
    private Label botScoreLabel;
    private Card blankCard;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setupMainView();

        deck = new Deck();
        player = new Player(null);
        botPlayer = new Player(null);

        player.setPlayerCardsList(deck.dealCards(player));
        botPlayer.setPlayerCardsList(deck.dealCards(botPlayer));

        Card faceUpCard = deck.dealCard(null);
        deck.addCardToPile(faceUpCard);
        deckPane.add(faceUpCard.getFaceImage(), 1, 0);
        System.out.println("CARD: " + deck.getPileArrayList().get(0).getId());
        assignPlayerCards();

        //todo add in floating cards
        for (int i = 0; i < botPlayer.getPlayerCardsList().size(); i++) {
//            Card card = new Card(null, 127);
//            ImageView imageView  = new ImageView(card.getFaceImage());
//            botHandHBox.getChildren().add(imageView);
            botHandHBox.getChildren().add(botPlayer.getPlayerCardsList().get(i).getFaceImage());
        }
    }

    private void assignPlayerCards() {
        for (int i = 0; i < player.getPlayerCardsList().size(); i++) {
            Card card = player.getPlayerCardsList().get(i);
            ImageView imageView = card.getFaceImage();

            int index = i;
            playerHandHBox.getChildren().add(imageView);
            playerHandHBox.getChildren().get(i).setOnMouseClicked(e -> {
                deckPane.add(imageView, 1, 0);
                deck.addCardToPile(card);
                //todo put back
//                player.removeCard(index);
                Player winner = deck.checkForWin();
                if (winner != null) {
                    System.out.println("win " + winner.getScore() + " " + winner);
                    Card newCard = deck.dealCard(null);
                    deck.addCardToPile(newCard);
                    deckPane.add(blankCard.getFaceImage(), 1, 0);
                    deckPane.add(newCard.getFaceImage(), 1, 0);
                    playerScoreLabel.setText("Points: " + player.getScore());

                    playBot(false);
                } else {
                    playBot(true);
                }

                playerHandHBox.getChildren().remove(imageView);

                if (playerHandHBox.getChildren().size() == 0) {
                    System.out.println("GCL: " + deck.getCardList().size());
                    if (deck.getCardList().size() >= 8) {
                        player.setPlayerCardsList(deck.dealCards(player));
                        assignPlayerCards();
                    } else {
                        determineWinner();
                    }
                }
            });
        }
    }

    private void playBot(boolean checkForWin) {
        System.out.println("SL: " + botPlayer.getPlayerCardsList().size());
        Ai ai = new Ai(botPlayer.getPlayerCardsList());
        Random random = new Random();
        int randomInt = random.nextInt(botPlayer.getPlayerCardsList().size());
        Card card;

        if (ai.pickCard(deck) != -1) {
            int id = ai.pickCard(deck);
            card = botPlayer.getPlayerCardsList().get(id);
            botHandHBox.getChildren().remove(id);
            botPlayer.removeCard(id);
        } else {
            card = botPlayer.getPlayerCardsList().get(randomInt);
            botHandHBox.getChildren().remove(randomInt);
            botPlayer.removeCard(randomInt);
        }
        ImageView imageView = card.getFaceImage();

        deckPane.add(imageView, 1, 0);
//        botHandHBox.getChildren().remove(randomInt);
//        botPlayer.removeCard(randomInt);
        deck.addCardToPile(card);

        if (botPlayer.getPlayerCardsList().size() == 0) {
            System.out.println("GCL: " + deck.getCardList().size());
            if (deck.getCardList().size() >= 8) {
                botPlayer.setPlayerCardsList(deck.dealCards(botPlayer));
                for (int i = 0; i < botPlayer.getPlayerCardsList().size(); i++) {
                    botHandHBox.getChildren().add(botPlayer.getPlayerCardsList().get(i).getFaceImage());
                }
            } else {
                determineWinner();
            }
        }

        if (checkForWin) {
            Player winner = deck.checkForWin();
            if (winner != null) {
                System.out.println("BOTwin " + winner.getScore() + " " + winner);
                botScoreLabel.setText("BotPoints: " + botPlayer.getScore());
            }
        }
    }

    //todo implement ui
    private void determineWinner() {
        if (player.getScore() > botPlayer.getScore()) {
            System.out.println("You win!");
        } else if (player.getScore() == botPlayer.getScore()) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("You loose :(");
        }
    }

    private void setupMainView() {
        borderPane = new BorderPane();
        scene = new Scene(borderPane);
        playerHandHBox = new HBox();
        botHandHBox = new HBox();
        rightBottomInfoVBox = new VBox();
        rightTopInfoVBox = new VBox();
        rightContainerVBox = new VBox();
        deckPane = new GridPane();
        deckHBox = new HBox();
        deckVBox = new VBox();
        logoVBox = new VBox();
        playerScoreLabel = new Label("Points: 0");
        botScoreLabel = new Label("BotPoints: 0");
        blankCard = new Card(null, 128);

        playerScoreLabel.setTextFill(Color.WHITE);
        botScoreLabel.setTextFill(Color.WHITE);

        ImageView deckCard = new ImageView("assets/images/card/b2fv.png");

        playerHandHBox.setStyle("-fx-background-color: #3ab503");
        playerHandHBox.alignmentProperty().setValue(Pos.CENTER);

        botHandHBox.setStyle("-fx-background-color: #3ab503");
        botHandHBox.alignmentProperty().setValue(Pos.CENTER);

        rightBottomInfoVBox.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
        rightBottomInfoVBox.getChildren().add(playerScoreLabel);

        rightTopInfoVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
        rightTopInfoVBox.getChildren().add(botScoreLabel);

        rightContainerVBox.setStyle("-fx-background-color: #84b581");
        rightContainerVBox.setPadding(new Insets(10, 10, 0, 10));
        rightContainerVBox.alignmentProperty().setValue(Pos.CENTER);
        rightContainerVBox.getChildren().addAll(rightTopInfoVBox, rightBottomInfoVBox);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setPercentWidth(50);

        deckPane.add(deckCard, 0, 0);
        deckPane.setPadding(new Insets(10, 10, 0, 10));

        deckHBox.alignmentProperty().setValue(Pos.CENTER);
        deckHBox.getChildren().add(deckPane);

        deckVBox.alignmentProperty().setValue(Pos.CENTER);
        deckVBox.getChildren().add(deckHBox);

        logoVBox.alignmentProperty().setValue(Pos.CENTER);
        Label logoLabel = new Label("Pishti");
        logoLabel.setRotate(-90);
        logoLabel.setStyle("-fx-font-size: 36px");
        logoLabel.setTextFill(Color.web("#84b581"));
        logoVBox.getChildren().add(logoLabel);
        logoVBox.prefWidthProperty().bind(rightContainerVBox.widthProperty());

        borderPane.setStyle("-fx-background-color: #0a6c03");
        borderPane.setBottom(playerHandHBox);
        borderPane.setTop(botHandHBox);
        borderPane.setRight(rightContainerVBox);
        borderPane.setCenter(deckVBox);
        borderPane.setLeft(logoVBox);
        borderPane.autosize();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pishti");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
