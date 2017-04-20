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
 * Tiili olio-luokka jossa luodaan tiili peliin ja määritetään tarvitaanko sen
 * tuhoamiseen 1 vai 2 iskua.
 *
 * @author Ilari
 */
public class Tiili {

    Rectangle tiili;
    int leveys;
    int korkeus;
    int sijaintiX;
    int sijaintiY;
    boolean tupla;  //tarvitaanko 2 iskua tuhoamiseen

    boolean tuhottu;
    static Bounds rajat;
    /**
     * Luo tiili.olion ja settaa normaalin orsanssiksi ja tuplan harmaaksi
     * @param sijaintiX horisontaalinen positio
     * @param sijaintiY vertikaalinen positio
     * @param leveys    tiilen leveys (vakio)
     * @param korkeus   tiilen korkeus (vakio)
     * @param tupla     onko tiili tupla vai ei (arvotaan peli luokassa)
     */
    public Tiili(int sijaintiX, int sijaintiY, int leveys, int korkeus, boolean tupla) {
        this.leveys = leveys;
        this.korkeus = korkeus;

        this.sijaintiX = sijaintiX;
        this.sijaintiY = sijaintiY;

        this.tupla = tupla;

        tuhottu = false;

        this.tiili = new Rectangle(this.sijaintiX, this.sijaintiY, leveys, korkeus);
        if (!tupla) {
            this.tiili.setFill(Color.ORANGE);
            this.tiili.setStroke(Color.BLACK);
        } else {
            this.tiili.setFill(Color.GREY);
            this.tiili.setStroke(Color.BLACK);
        }
    }
    /**
     * palauttaa onko tiili tuhottu vai ei
     * @return 
     */
    public boolean isTuhottu() {
        return tuhottu;
    }
    /**
     * asettaa tuhottu arvon (ei suositella)
     * @param isTuhottu 
     */
    public void setIsTuhottu(boolean isTuhottu) {
        this.tuhottu = isTuhottu;
    }
    /**
     * Tuhoaa tiilen eli poistaa sen näkyvistä ja asettaa tuhottu booleanin todeksi jottei siihen voi osua.
     * Lisää käyttäjälle palkinnoksi 10 pistettä
     */
    public void Tuhoa() {
        this.tiili.setVisible(false);
        this.tiili.setDisable(true);
        this.tuhottu = true;
        peli.pisteet.LisaaPiste(10);    // lisää 10 pistettä
    }
    /**
     * tutkii osuuko pallo tiileen ja jos kyllä, kimmotetaan pallo ja tuhotaan tiiltä.
     * @param pallo osuva pallo
     */
    public void Collide(Pallo pallo) {
        //tutkitaan osuiko pallo tiileen
        rajat = this.tiili.getBoundsInParent();
        if (rajat.intersects(pallo.pallo.getBoundsInParent())) {
            boolean vasen = rajat.getMinX() >= pallo.pallo.getLayoutX();
            boolean oikea = rajat.getMaxX() <= pallo.pallo.getLayoutX();
            boolean yla = rajat.getMaxY() <= pallo.pallo.getLayoutY();
            boolean ala = rajat.getMinY() >= pallo.pallo.getLayoutY();
            if (ala || yla) {
                pallo.moveAmount *= -1;  //kimmotetaan pallo tiilestä
            } else if (vasen || oikea) {
                pallo.muutos *= -1;
            }
            if (pallo.active) {
                if (!this.tupla) {
                    this.Tuhoa();   //tuhotaan tiili
                } else {    
                    this.tiili.setFill(Color.ORANGE);   //jos pallo oli tupla muutetaan se normaaliksi
                    this.tupla = false;
                }
            }
        }
    }
}
