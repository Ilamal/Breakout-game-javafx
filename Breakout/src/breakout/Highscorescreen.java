/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import static breakout.Tallennus.tallennuspolku;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.Serializable;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Pistetilaston näyttämiseen tehty luokka
 * @author Ilari
 */
public class Highscorescreen extends Application implements Serializable {
    static int il=1;
    /**
     * start-metodi leaderboards scenen näyttämiseen
     * @param primaryStage
     * @throws IOException 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane root = new BorderPane();
        VBox boksi = new VBox(5);
        ArrayList<Pisteet> pisteet = new ArrayList();
        boolean tiedosto = false;
        
            ObjectInputStream os;
       
            Object obj;
            try{
            os = new ObjectInputStream(new FileInputStream(tallennuspolku));
            
            while(!tiedosto){
                     obj=null;
                try {
                    obj = os.readObject();
                                       
                }catch(ClassNotFoundException | EOFException ex){
                    
                } 
                    if (obj != null)
                        pisteet.add((Pisteet)obj);
                    else{
                        os.close();
                        tiedosto = true;
                    }
                        
            }
            }catch (FileNotFoundException | NullPointerException ex) {
                System.out.println("Tiedostoa ei löytynyt tai se on tyhjä. Muuta tiedosto-polkua Tallennus luokassa tai pelaa saadaksesi tuloksia");
                Menu menu = new Menu();
                menu.start(primaryStage);
            }
                
        if(pisteet.size()>0){                            
        if(pisteet.size()>1){
            Collections.sort(pisteet);      //lajitellaan pisteiden mukaan
        }
        Collections.reverse(pisteet);       // asetetaam isoimmat ekaksi
        ArrayList<Text> tekstit = new ArrayList();
        il=1;
        pisteet.forEach((p) -> {            
            tekstit.add(new Text(il+". "+p.pelaajaNimi+"\t - \t"+p.pisteet+"p"));
            il++;
        });
        tekstit.forEach((t)->{
            t.setFont(Font.font("Arial",30));
            boksi.getChildren().add(t);
        });
        }
        Button menuun = new Button();
        menuun.setPrefSize(100, 30);
        menuun.setText("Menuun");
        menuun.setTranslateY(450);
        menuun.setOnMouseClicked(e->{
             Menu menu = new Menu();
             menu.start(primaryStage);
        });
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setLeft(boksi);
        root.setRight(menuun);
        Scene scene = new Scene(root, peli.WINDOWLEVEYS, peli.WINDOWKORKEUS);
        
        primaryStage.setTitle("Leaderboards!");
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String [] args){
        launch(args);
    }
    
}

