
package breakout;

import static breakout.peli.pelaaja;
import static breakout.peli.peliAlusta;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 *  Pelaaja-luokka joka toimii pelaaja luokkana. Omaa laudan jota pelaaja liikuttelee ja se kimmottaa palloa.
 *  staattinen luokka ei muuttuvia arvoja luonnissa
 * @author Ilari
 */
public class Pelaaja {
    Rectangle lauta;
    static double sijaintiX=peli.WINDOWLEVEYS/2.0-42;   //haetaan asetettava X koordinaatti pelaajalle, jotta se tulisi keskelle ruutua. KOVAKOODATTU ARVO POISTA
    static int sijaintiY=485;   //kiva positio pelaajalle
    static float speed=4;   //Turha hiiri liikkumisessa
    Bounds pelirajat;   //ikkunan rajat
    
    int leveys=70;
    int korkeus =10;
    public Pelaaja() {       
        this.lauta = new Rectangle(sijaintiX,sijaintiY,leveys,korkeus);     // luo laudan eli suorakulmion, kovakoodatuilla arvoilla, jota sitten liikutellaan
    }
    /**
     * Liikuttaa pelaajan lautaa käytäjän hiiren mukana. Jos hiiri menee liian kauas lopetetaan seuraaminen.
     */
    public void LiikutaPelaajaa(){
        pelirajat=peli.peliAlusta.getBoundsInParent();
        peliAlusta.setOnMouseMoved(e->{                         //tutkii onko hiiri liian kaukana          
                if (e.getSceneX() > pelirajat.getMinX()+(pelaaja.lauta.getWidth()/2) && e.getSceneX() < pelirajat.getMaxX()-(pelaaja.lauta.getWidth()/2) && !peli.peliloppu){
                    pelaaja.lauta.setLayoutX(e.getSceneX()-(peli.WINDOWLEVEYS/2));         //asettaa laudan hiiren sijaintiin      
         }
        });       
    }
}
