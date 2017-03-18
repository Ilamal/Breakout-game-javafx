/*
 *
 * Pisteen lasku
 * 
 */
package breakout;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ilari
 */
public class Pisteet {
    Text pisteTeksti;
    public int pisteet;
    String pelaajaNimi;

    public Pisteet() {
        pisteTeksti = new Text("0");
        pisteTeksti.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        pisteTeksti.setVisible(true);
        pisteTeksti.toFront();
        pisteTeksti.setTextOrigin(VPos.TOP);
        pisteTeksti.setLayoutX(pisteTeksti.getLayoutX()+(peli.WINDOWLEVEYS-100));
    }

    public Text getPisteTeksti() {
        return pisteTeksti;
    }

    public int getPisteet() {
        return pisteet;
    }
    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }
    public void Update(){
        pisteTeksti.setText(String.valueOf(pisteet));
    }
    public void LisaaPiste(int pisteet){
        this.pisteet+=pisteet;
        Update();
    }
    
}
