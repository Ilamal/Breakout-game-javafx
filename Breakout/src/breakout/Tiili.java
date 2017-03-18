/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import static breakout.peli.pelaaja;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Ilari
 */
public class Tiili {
    Rectangle tiili;
    int leveys;
    int korkeus;
    int sijaintiX;    
    int sijaintiY;
    boolean tupla;
    
    boolean tuhottu;
    static Bounds rajat;
    public Tiili(int sijaintiX, int sijaintiY,int leveys,int korkeus,boolean tupla) {
        this.leveys = leveys;
        this.korkeus =korkeus;
        
        this.sijaintiX = sijaintiX;
        this.sijaintiY = sijaintiY;
        
        this.tupla = tupla;
        
        tuhottu = false;
        
        this.tiili = new Rectangle(this.sijaintiX,this.sijaintiY,leveys,korkeus);
        if(!tupla){
            this.tiili.setFill(Color.ORANGE);
            this.tiili.setStroke(Color.BLACK);
        }
        else {
            this.tiili.setFill(Color.GREY);
            this.tiili.setStroke(Color.BLACK);
        }
    }
    
    public boolean isTuhottu() {
        return tuhottu;
    }

    public void setIsTuhottu(boolean isTuhottu) {
        this.tuhottu = isTuhottu;
    }
    public void Tuhoa(){
        this.tiili.setVisible(false);
        this.tiili.setDisable(true);
        this.tuhottu = true;
        peli.pisteet.LisaaPiste(10);    // lisää 10 pistettä
    }
    public void Collide(Pallo pallo){
            //tutkitaan osuiko pallo tiileen
            rajat = this.tiili.getBoundsInParent();
        if (rajat.intersects(pallo.pallo.getBoundsInParent())){
            boolean vasen = rajat.getMinX()>=pallo.pallo.getLayoutX();
            boolean oikea = rajat.getMaxX()<=pallo.pallo.getLayoutX();
            boolean yla = rajat.getMaxY()<=pallo.pallo.getLayoutY();
            boolean ala = rajat.getMinY()>=pallo.pallo.getLayoutY();
            if(ala || yla)
                pallo.moveAmount*=-1;  //kimmotetaan pallo tiilestä
            else if(vasen || oikea)
                pallo.muutos*=-1;
            if (pallo.active){
                if(!this.tupla)
                    this.Tuhoa();   //tuhotaan tiili
                else{
                    this.tiili.setFill(Color.ORANGE);
                    this.tupla = false;
              }
            }
            System.out.println("Tiili osuma");  //debug
        }                    
    }
}
