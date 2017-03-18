/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import static breakout.peli.pallo;
import static breakout.peli.pallomaara;
import static breakout.peli.peliAlusta;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 *
 * @author Ilari
 */
public class Pallo {

    int sade;
    Circle pallo;

    float moveAmount;   //liike nopeus
    float maxMuutos;    // maksimaalinen horisontaalinen muutos nopeudessa
    float muutos;   // asetetaan törmäysen yhteydessä riippuen törmäyksestä

    boolean active; //onko pallo aktiivinen eli voiko se tuhota tiiliä
    
    public static Bounds rajat;
    
    public Pallo(int sade, float moveAmount, float maxMuutos) {
        this.sade = sade;
        this.moveAmount = moveAmount;
        this.maxMuutos = maxMuutos;

        this.pallo = new Circle(sade);
        this.pallo.setFill(Color.RED);
        
        this.active = false;
        
    }

    /**
     * Liikuttaa palloa asetetulla nopeudella
     */
    public void liiku() {
        this.pallo.setLayoutY(this.pallo.getLayoutY() + moveAmount);    // asettaa vertikaalisen liikkeen
        this.pallo.setLayoutX(this.pallo.getLayoutX() + (maxMuutos * this.muutos));    //asettaa horisontaalisen liikeen

    }

    public void onCollide(Tiili other) {
        this.moveAmount = -this.moveAmount;     //asettaa vertikaalisen liikkeen päinvastaiseksi
    }

    public void onCollide() {
        if(this.pallo.getBoundsInParent().intersects(peli.pelaaja.lauta.getBoundsInParent())){
        this.moveAmount = -this.moveAmount;     //asettaa vertikaalisen liikkeen päinvastaiseksi
        float muutos = clamp(Float.parseFloat(String.valueOf((((this.pallo.getLayoutX() - peli.pelaaja.lauta.getLayoutX()) - 305) / 37.5) - 1)), -0.5f, 0.5f) * 2f; //tutkii tarvittavan muutoksen horisontaalisessa liikkeessä
        if(muutos > -0.1 && muutos < 0.1)
            muutos =0;
        this.muutos = muutos;
        if(!this.active){
            this.active=true;
            peli.pallomaara++;
        }
        System.out.println(this.muutos);    //debug
        System.out.println(String.valueOf(this.pallo.getLayoutX() - peli.pelaaja.lauta.getLayoutX()));   //debug
        }
    }
    public void seinaCollide() {
        if (rajat == null){
            rajat = peli.peliAlusta.getBoundsInLocal(); //haetaan seinät
        }
        Boolean oikealla = this.pallo.getLayoutX() >= (rajat.getMaxX() - this.pallo.getRadius());
        Boolean vasemmalla = this.pallo.getLayoutX() <= (rajat.getMinX() + this.pallo.getRadius());
        Boolean ylhaalla = this.pallo.getLayoutY() <= (rajat.getMinY() + this.pallo.getRadius());
        Boolean alhaalla = this.pallo.getLayoutY() >= (rajat.getMaxY() - this.pallo.getRadius());
        if (oikealla || vasemmalla) {
            muutos *= -1;
        }
        if (ylhaalla) {
            moveAmount *= -1;
        }
        if(alhaalla && this.active){
            this.active =false;
            peli.pisteet.LisaaPiste(-15);
            peli.pallomaara--;
            if(peli.pallomaara <= 0){
                peli.peliloppu = true;
            }
        }
    }

    public int getSade() {
        return sade;
    }

    public void setSade(int sade) {
        this.sade = sade;
    }

    public Circle getPallo() {
        return pallo;
    }

    public void setPallo(Circle pallo) {
        this.pallo = pallo;
    }

    public void setMoveAmount(float moveAmount) {
        this.moveAmount = moveAmount;
    }
    public float getMoveAmount() {
        return this.moveAmount;
    }
    /**
     * Tehty "clamp" funktio, koska javassa ei ole valmiiina. Hakee halutun
     * arvon väliltä
     *
     * @param val
     * @param min
     * @param max
     * @return
     */
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
