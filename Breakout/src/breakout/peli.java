/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;


import static breakout.peli.pallo;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Ilari
 */
public class peli extends Application {
    static final int WINDOWLEVEYS=700;
    static final int WINDOWKORKEUS=550;
    public static Pane peliAlusta;
    
    static Pisteet pisteet = new Pisteet();
    static Pallo pallo = new Pallo(4,3f,1.5f);
    static Pallo pallo2 = new Pallo(4,-3f,1.5f);
    static Pelaaja pelaaja = new Pelaaja();
    
    static int rivi=1;     //tiilien määrä jokaisessa rivissä 9
    static int rivit=1;     //tiili rivien määrä 8
    
    static Tiili [] tiilet;
    static int i;
    static long aika;
    static long aika2;
    static int pallomaara=0;
    static public boolean peliloppu=false;
    Timeline animation;
    static Random rng = new Random();
    @Override
    public void start(Stage primaryStage) {
        
        peliAlusta = new Pane();                                
        pallo.pallo.relocate(WINDOWLEVEYS/2, WINDOWKORKEUS/2);
        pallo2.pallo.relocate(WINDOWLEVEYS/2, 15);
       // pallo.active=true; pitää ratkaista jos pallo menee suoraan ohi jotenkin muuten
        
        GenerateMap();
        AlkuTeksti();
        aika = System.currentTimeMillis();
        aika2 = System.currentTimeMillis();
        Scene scene = new Scene(peliAlusta, WINDOWLEVEYS, WINDOWKORKEUS);
        EventHandler<ActionEvent> tapahtumankasittelija = e -> {
           if(!peliloppu)
                update();
           else{
               Loppusivu(scene);
               animation.stop();
           }
        };
        
        animation = new Timeline(new KeyFrame(Duration.millis(8), tapahtumankasittelija));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();                            
        peliAlusta.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        peliAlusta.getChildren().addAll(pallo.pallo,pallo2.pallo);
        peliAlusta.getChildren().add(pelaaja.lauta);
        
        primaryStage.setMaxHeight(WINDOWKORKEUS);
        primaryStage.setMaxWidth(WINDOWLEVEYS);
        scene.setCursor(Cursor.NONE);
        pelaaja.LiikutaPelaajaa();  //  luo liikkumisen pelaajalle
        
        primaryStage.setTitle("Peli käynnissä");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    static public void update(){
        if(System.currentTimeMillis()-aika2>3000){
            pallo.liiku();           //luo pallon liikeen
        }
            pallo2.liiku();
            
            pallo.onCollide();      //
            pallo2.onCollide();
            
            pallo.seinaCollide();
            pallo2.seinaCollide();
            
            int tiililaskuri=0;     //laskuri jolla katsotaan onko kaikki tiilet tuhottu
            for(i=0;i<tiilet.length;i++){       //tutkii onko tiiltä vielä tuhottu ja jos ei niin pallo voi osua siihen
                if(!tiilet[i].isTuhottu()){
                    tiilet[i].Collide(pallo);
                    tiilet[i].Collide(pallo2);
                }
                else{
                    tiililaskuri++;     //tutkitaan onko kaikki tiilet tuhottu
                }
                if (tiililaskuri>=tiilet.length){
                    peliloppu = true;
                }
    }
            if((System.currentTimeMillis()-aika)>=10000){
                if(pisteet.getPisteet()>=1)
                    pisteet.LisaaPiste(-5);
                aika=System.currentTimeMillis();
            }
            

}
    
    static public void GenerateMap(){
        int i,j,y=50,x=0;
        
        tiilet = new Tiili[rivi*rivit];
        
        for(i=0;i<rivit;i++){
        for(j=0;j<rivi;j++){
            tiilet[x] = new Tiili((25+j*70),y,70,20,rng.nextBoolean());
            x++;
        }
        y+=20;  //rivin vaihto
        }
        for (i=0;i<tiilet.length;i++){
            peliAlusta.getChildren().add(tiilet[i].tiili);
        }
        peliAlusta.getChildren().add(pisteet.pisteTeksti);
    } 
    static public void AlkuTeksti(){
        Text alkuteksti = new Text("3");
        alkuteksti.setFont(Font.font("Arial", FontWeight.BOLD, 75));
        alkuteksti.toFront();
        alkuteksti.setLayoutY(alkuteksti.getLayoutY()+WINDOWKORKEUS/2);
        alkuteksti.setLayoutX(alkuteksti.getLayoutX()+WINDOWLEVEYS/2-20);
        alkuteksti.setTextAlignment(TextAlignment.CENTER);
        EventHandler<ActionEvent> alkutekstikasittelija = e -> {
            int teksti=0;
                 if(!alkuteksti.getText().equals("GO"))
                    teksti = Integer.parseInt(alkuteksti.getText())-1;
                 if(alkuteksti.getText().equals("1")){
                     alkuteksti.setText("GO");
                     alkuteksti.setLayoutX(alkuteksti.getLayoutX()-25);
                 }
                 else if(alkuteksti.getText().equals("GO"))
                     alkuteksti.setText("");
                 else
                    alkuteksti.setText(String.valueOf(teksti));     
        };
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), alkutekstikasittelija));
        tl.setCycleCount(4);
        tl.play();
        peliAlusta.getChildren().add(alkuteksti);
        }
    
    static public void Loppusivu(Scene scene){
        
        Text teksti = new Text("Peli loppui. Pisteesi ovat : " + pisteet.pisteet);
        teksti.setFont(Font.font("Arial",FontWeight.BOLD,35));
        peliAlusta.getChildren().add(teksti);
        teksti.setLayoutY(200);
        teksti.setLayoutX(90);
        
        Button tallennus = new Button("Tallenna");  //paremman näköiseksi voisi tehdä ja vaihtaa position

        tallennus.setLayoutY(300);
        tallennus.setLayoutX(325);
        peliAlusta.getChildren().add(tallennus);
        
        TextField tf = new TextField("Anna nimesi");
        tf.setLayoutX(275);
        tf.setLayoutY(250);
        peliAlusta.getChildren().add(tf);
        
        tf.setOnAction(e->{
            pisteet.pelaajaNimi = tf.getText();
        });
        
        scene.setCursor(Cursor.DEFAULT);
        
        tallennus.setOnMouseClicked(e->{
            pisteet.pelaajaNimi = tf.getText();
            if(pisteet.pelaajaNimi!=null && pisteet.pelaajaNimi.length() > 3 && !pisteet.pelaajaNimi.equals("Anna nimesi")){
                Tallennus talletus = new Tallennus(pisteet);
                talletus.start(new Stage());
            }
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
