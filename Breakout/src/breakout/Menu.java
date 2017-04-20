/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import static breakout.peli.peliloppu;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Menu ikkuna, missä on valinnat liikkua peliin tai katsoa pistetilastoja
 * Ohjelman pääluokka, jolla pitää aloittaa.
 * @author IlaMal
 */
public class Menu extends Application {

    static public Stage primStage;
    static peli peli = new peli();
    @Override
    public void start(Stage primaryStage) {
        primStage = primaryStage;
        FlowPane root = new FlowPane();
        Text teksti = new Text("Tiilipeli");
        teksti.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        teksti.setFill(Color.DARKCYAN);
        teksti.setTranslateY(-80);
        
        VBox boksi = new VBox(15);
        boksi.getChildren().add(teksti);
        boksi.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        
        Button pelaa = new Button("Pelaa!");
        Button highscore = new Button("Pistetilastot");
        pelaa.setPrefSize(100, 30);
        highscore.setPrefSize(100, 30);
        
        boksi.getChildren().addAll(pelaa, highscore);

        pelaa.setOnMouseClicked(e -> {
            peliloppu = false;
            peli.start(primaryStage);
        });
        highscore.setOnMouseClicked(e -> {
            Highscorescreen high = new Highscorescreen();
            try {
                high.start(primStage);
            } catch (IOException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        root.getChildren().add(boksi);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root, peli.WINDOWLEVEYS, peli.WINDOWKORKEUS);

        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
