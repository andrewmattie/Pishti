package com.andrewmattie;

import com.andrewmattie.objects.Card;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setupMainView();


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
        Label playerScoreLabel = new Label("Points: 0");
        Label botScoreLabel = new Label("BotPoints: 0");
        Player player = new Player(null);
        Player botPlayer = new Player(null);

        playerScoreLabel.setTextFill(Color.WHITE);
        botScoreLabel.setTextFill(Color.WHITE);

        Card card = new Card(null, 34);
        Card card2 = new Card(null, 35);
        Card card3 = new Card(null, 36);
        Card card4 = new Card(null, 37);
        Card card5 = new Card(null, 38);
        Card card6 = new Card(null, 39);
        Card card7 = new Card(null, 40);
        Card card8 = new Card(null, 41);

        ImageView deckCard = new ImageView("assets/images/card/b2fv.png");
        Card faceUpCard = new Card(null, 29);

        playerHandHBox.setStyle("-fx-background-color: #3ab503");
        playerHandHBox.alignmentProperty().setValue(Pos.CENTER);
        playerHandHBox.getChildren().addAll(card.getFaceImage(), card2.getFaceImage(), card3.getFaceImage(), card7.getFaceImage());

        botHandHBox.setStyle("-fx-background-color: #3ab503");
        botHandHBox.alignmentProperty().setValue(Pos.CENTER);
        botHandHBox.getChildren().addAll(card4.getFaceImage(), card5.getFaceImage(), card6.getFaceImage(), card8.getFaceImage());

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
        deckPane.add(faceUpCard.getFaceImage(), 1, 0);

        deckHBox.alignmentProperty().setValue(Pos.CENTER);
        deckHBox.getChildren().add(deckPane);

        deckVBox.alignmentProperty().setValue(Pos.CENTER);
        deckVBox.getChildren().add(deckHBox);

        logoVBox.alignmentProperty().setValue(Pos.CENTER);
        Label logoLabel = new Label("Pishti");
        logoLabel.setRotate(-90);
        logoLabel.setStyle("-fx-font-size: 36px");
        logoLabel.setTextFill(Color.web("#d3d3d3"));
        logoVBox.getChildren().add(logoLabel);
        logoVBox.prefWidthProperty().bind(rightContainerVBox.widthProperty());

        borderPane.setStyle("-fx-background-color: #0a6c03");
        borderPane.setBottom(playerHandHBox);
        borderPane.setTop(botHandHBox);
        borderPane.setRight(rightContainerVBox);
        borderPane.setCenter(deckVBox);
        borderPane.setLeft(logoVBox);
        borderPane.autosize();

        playerScoreLabel.setText("Points: " + player.getScore());
        botScoreLabel.setText("BotPoints: " + botPlayer.getScore());

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
