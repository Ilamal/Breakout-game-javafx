/*
 *
 * Pisteen lasku
 * 
 */
package breakout;

import java.io.Serializable;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.Serializable;

/**
 * Pisteet olio pelaajan pisteen laskentaan ja nimen tallentamiseen
 * @author Ilari
 */
public class Pisteet implements Serializable,Comparable<Pisteet>{
    static Text pisteTeksti;
    public int pisteet;
    String pelaajaNimi;
    /**
     * Luodaan pisteolio ja sen teksti, joka näkyy pelin ylänurkassa
     */
    public Pisteet() {
        pisteTeksti = new Text("0");
        pisteTeksti.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        pisteTeksti.setVisible(true);
        pisteTeksti.toFront();
        pisteTeksti.setTextOrigin(VPos.TOP);
        pisteTeksti.setLayoutX(pisteTeksti.getLayoutX()+(peli.WINDOWLEVEYS-100));
    }
    /**
     * palauttaa pistetekstin
     * @return 
     */
    public Text getPisteTeksti() {
        return pisteTeksti;
    }
    /**
     * palauttaa pisteet
     * @return 
     */
    public int getPisteet() {
        return pisteet;
    }
    /**
     * asettaa pisteet (ei suositella)
     * @param pisteet 
     */
    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }
    /**
     * updatettaa pisteteksin ajantasalle
     */
    public void Update(){
        pisteTeksti.setText(String.valueOf(pisteet));
    }
    /**
     * Lisää tietyn verran pisteteitä ja päivittää pistetekstin. Käytä aina ennemmin kuin setPisteet.
     * @param pisteet 
     */
    public void LisaaPiste(int pisteet){
        this.pisteet+=pisteet;
        Update();
    }
    /**
     * uusi vertailu metodi, jotta voidaan asettaa oliot järjestykseen pisteiden perusteella.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Pisteet o) {
         return this.getPisteet()-o.getPisteet();
    }

   
    

}
