package com.andrewmattie;

import com.andrewmattie.objects.Card;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Shadaman Rahman on 4/18/2017.
 */
public class Layout extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        HBox playerHandHBox = new HBox();
        HBox botHandHBox = new HBox();
        VBox rightBottomInfoVBox = new VBox();
        VBox rightTopInfoVBox = new VBox();
        VBox rightContainerVBox = new VBox();
        GridPane deckPane = new GridPane();
        HBox deckHBox = new HBox();
        VBox deckVBox = new VBox();
        Label playerScoreLabel = new Label("Points: 0");
        Label botScoreLabel = new Label("BotPoints: 0");

        Card card = new Card(null, 34);
        Card card2 = new Card(null, 35);
        Card card3 = new Card(null, 36);
        Card card4 = new Card(null, 37);
        Card card5 = new Card(null, 38);
        Card card6 = new Card(null, 39);

        ImageView deckCard = new ImageView("assets/images/card/b2fv.png");
        Card faceUpCard = new Card(null, 29);

        playerHandHBox.alignmentProperty().setValue(Pos.CENTER);
        playerHandHBox.getChildren().addAll(card.getFaceImage(), card2.getFaceImage(), card3.getFaceImage());

        botHandHBox.alignmentProperty().setValue(Pos.CENTER);
        botHandHBox.getChildren().addAll(card4.getFaceImage(), card5.getFaceImage(), card6.getFaceImage());

        rightBottomInfoVBox.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
        rightBottomInfoVBox.getChildren().add(playerScoreLabel);

        rightTopInfoVBox.alignmentProperty().setValue(Pos.TOP_CENTER);
        rightTopInfoVBox.getChildren().add(botScoreLabel);

        rightContainerVBox.alignmentProperty().setValue(Pos.CENTER);
        rightContainerVBox.getChildren().addAll(rightTopInfoVBox, rightBottomInfoVBox);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        columnConstraints.setPercentWidth(50);

        deckPane.add(deckCard, 0, 0);
        deckPane.add(faceUpCard.getFaceImage(), 1, 0);

        deckHBox.alignmentProperty().setValue(Pos.CENTER);
        deckHBox.getChildren().add(deckPane);

        deckVBox.alignmentProperty().setValue(Pos.CENTER);
        deckVBox.getChildren().add(deckHBox);

        borderPane.setBottom(playerHandHBox);
        borderPane.setTop(botHandHBox);
        borderPane.setRight(rightContainerVBox);
        borderPane.setCenter(deckVBox);
        borderPane.setPadding(new Insets(10, 10, 0, 10));
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
