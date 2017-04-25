// Class: CS 1302/03
// Teammates: Andrew, Gianni, Jason, Shadaman

package com.andrewmattie;

import com.andrewmattie.objects.Ai;
import com.andrewmattie.objects.Card;
import com.andrewmattie.objects.Deck;
import com.andrewmattie.objects.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


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
    private Label logoLabel;
    private String playerName = "Player";


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setupMainView();
        generateGame();
    }

    private void assignPlayerCards() {
        for (int i = 0; i < player.getPlayerCardsList().size(); i++) {
            Card card = player.getPlayerCardsList().get(i);
            ImageView imageView = card.getFaceImage();

            playerHandHBox.getChildren().add(imageView);
            playerHandHBox.getChildren().get(i).setOnMouseClicked(e -> {
                changePlayerHandState(true);
                deckPane.add(imageView, 1, 0);
                deck.addCardToPile(card);

                Player winner = deck.checkForWin();
                checkScore(winner);

                playerHandHBox.getChildren().remove(imageView);
                playerScoreLabel.setStyle("-fx-underline: false");
                botScoreLabel.setStyle("-fx-underline: true");

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            playBot();
                            changePlayerHandState(false);
                        });
                    }
                }, 1000);
            });
        }
    }

    private void checkScore(Player winner) {
        if (winner != null) {
            if (winner == player) {
                playerScoreLabel.setText(playerName + ": " + player.getScore());
            } else if (winner == botPlayer) {
                botScoreLabel.setText("Scrappy: " + botPlayer.getScore());
            }

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        Card newCard = deck.dealCard(null);
                        deck.addCardToPile(newCard);
                        deckPane.add(blankCard.getFaceImage(), 1, 0);
                        deckPane.add(newCard.getFaceImage(), 1, 0);
                    });
                }
            }, 500);
        }
    }

    private void playBot() {
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
        deck.addCardToPile(card);

        if (botPlayer.getPlayerCardsList().size() == 0) {
            if (deck.getCardList().size() >= 8) {
                botPlayer.setPlayerCardsList(deck.dealCards(botPlayer));
                player.setPlayerCardsList(deck.dealCards(player));
                assignPlayerCards();

                for (int i = 0; i < botPlayer.getPlayerCardsList().size(); i++) {
                    Card blankCard = new Card(null, 127);
                    botHandHBox.getChildren().add(blankCard.getFaceImage());
                }
            } else {
                determineWinner();
            }
        }

        Player winner = deck.checkForWin();
        checkScore(winner);

        playerScoreLabel.setStyle("-fx-underline: true");
        botScoreLabel.setStyle("-fx-underline: false");
    }

    private void determineWinner() {
        Stage dialogStage = new Stage();
        VBox vBox = new VBox();
        Scene dialogScene = new Scene(vBox, 200, 125);
        Label titleLabel = new Label();
        Label scoreLabel = new Label();
        Button newGameButton = new Button("New game");
        Button exitGameButton = new Button("Exit");

        titleLabel.prefWidthProperty().bind(dialogStage.widthProperty());
        scoreLabel.prefWidthProperty().bind(dialogStage.widthProperty());
        newGameButton.prefWidthProperty().bind(dialogStage.widthProperty());
        exitGameButton.prefWidthProperty().bind(dialogStage.widthProperty());

        titleLabel.setStyle("-fx-font-size: 24px");

        if (player.getScore() > botPlayer.getScore()) {
            titleLabel.setText("You win!");
        } else if (player.getScore() == botPlayer.getScore()) {
            titleLabel.setText("It's a tie!");
        } else {
            titleLabel.setText("You loose :(");
        }

        scoreLabel.setText(String.format("Scrappy: %s\n%s: %s", botPlayer.getScore(), playerName, player.getScore()));

        vBox.getChildren().addAll(titleLabel, scoreLabel, newGameButton, exitGameButton);

        Card blankCard = new Card(null, 128);
        deckPane.add(blankCard.getFaceImage(), 0, 0);
        deckPane.add(blankCard.getFaceImage(), 1, 0);

        newGameButton.setOnMouseClicked(event -> {
            dialogStage.close();
            deckPane.getChildren().clear();
            generateGame();
        });

        exitGameButton.setOnMouseClicked(event -> {
            dialogStage.close();
            primaryStage.close();
        });

        dialogStage.setResizable(false);
        dialogStage.setOnCloseRequest(Event::consume);
        dialogStage.setTitle("Thanks for playing!");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void changePlayerHandState(boolean disabled) {
        ObservableList<Node> observableList = playerHandHBox.getChildren();
        for (Node node : observableList) {
            node.setDisable(disabled);
        }
    }

    private void setupNameDialog() {
        Stage dialogStage = new Stage();
        VBox vBox = new VBox();
        Scene dialogScene = new Scene(vBox, 250, 60);

        TextField textField = new TextField();
        Button setNameButton = new Button("Play");

        textField.prefWidthProperty().bind(dialogStage.widthProperty());
        setNameButton.prefWidthProperty().bind(textField.widthProperty());
        dialogStage.setResizable(false);
        dialogStage.setOnCloseRequest(Event::consume);

        setNameButton.setOnMouseClicked(click -> {
            playerName = textField.getText();
            playerScoreLabel.setText(playerName + ": " + player.getScore());
            dialogStage.close();
        });

        textField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                playerName = textField.getText();
                playerScoreLabel.setText(playerName + ": " + player.getScore());
                dialogStage.close();
            }
        });

        vBox.getChildren().addAll(textField, setNameButton);

        dialogStage.setTitle("Please enter your name...");
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void generateGame() {
        deck = new Deck();
        player = new Player(null);
        botPlayer = new Player(null);

        player.setPlayerCardsList(deck.dealCards(player));
        botPlayer.setPlayerCardsList(deck.dealCards(botPlayer));

        Card faceUpCard = deck.dealCard(null);
        Card upsideDownCard = new Card(null, 127);
        deck.addCardToPile(faceUpCard);
        deckPane.add(upsideDownCard.getFaceImage(), 0, 0);
        deckPane.add(faceUpCard.getFaceImage(), 1, 0);
        assignPlayerCards();

        for (int i = 0; i < botPlayer.getPlayerCardsList().size(); i++) {
            Card card = new Card(null, 127);
            botHandHBox.getChildren().add(card.getFaceImage());
        }

        playerScoreLabel.setText(playerName + ": " + botPlayer.getScore());
        botScoreLabel.setText("Scrappy: " + botPlayer.getScore());
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
        playerScoreLabel = new Label(playerName + ": 0");
        botScoreLabel = new Label("Scrappy: 0");
        blankCard = new Card(null, 128);

        playerScoreLabel.setTextFill(Color.WHITE);
        playerScoreLabel.setStyle("-fx-underline: true");
        botScoreLabel.setTextFill(Color.WHITE);

        ImageView deckCard = new ImageView("assets/images/card/b2fv.png");

        playerHandHBox.setStyle("-fx-background-color: #3ab503");
        playerHandHBox.alignmentProperty().setValue(Pos.CENTER);
        playerHandHBox.setSpacing(5);

        botHandHBox.setStyle("-fx-background-color: #3ab503");
        botHandHBox.alignmentProperty().setValue(Pos.CENTER);
        botHandHBox.setSpacing(5);

        rightBottomInfoVBox.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
        rightBottomInfoVBox.getChildren().add(playerScoreLabel);

        rightTopInfoVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
        rightTopInfoVBox.getChildren().add(botScoreLabel);

        rightContainerVBox.setStyle("-fx-background-color: #84b581");
        rightContainerVBox.setPadding(new Insets(10, 10, 0, 10));
        rightContainerVBox.alignmentProperty().setValue(Pos.CENTER);
        rightContainerVBox.getChildren().addAll(rightTopInfoVBox, rightBottomInfoVBox);
        rightContainerVBox.setSpacing(50);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setPercentWidth(50);

        deckPane.add(deckCard, 0, 0);
        deckPane.setPadding(new Insets(10, 10, 0, 10));
        deckPane.setHgap(20);

        deckHBox.alignmentProperty().setValue(Pos.CENTER);
        deckHBox.getChildren().add(deckPane);

        deckVBox.alignmentProperty().setValue(Pos.CENTER);
        deckVBox.getChildren().add(deckHBox);

        logoVBox.alignmentProperty().setValue(Pos.CENTER);
        logoLabel = new Label("Pishti");
        logoLabel.setRotate(-90);
        logoLabel.setStyle("-fx-font-size: 28px");
        logoLabel.setTextFill(Color.web("#84b581"));
        logoVBox.getChildren().add(logoLabel);
        logoVBox.prefWidthProperty().bind(rightContainerVBox.widthProperty());
        logoVBox.prefHeightProperty().bind(rightContainerVBox.heightProperty());

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

        setupNameDialog();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
