package breakout;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tallennus luokka, jossa tallennetaan annetut pisteet valittuun tiedostoon
 * Omaa tiedostonpolun johon piste-oliot tallennetaa. Voi muuttaa haluamaksi.
 *
 * @author Ilari
 */
public class Tallennus implements Serializable {

    Pisteet pisteet;
    static public String tallennuspolku = "C:/work/breakoutpisteet.dat";    // tiedostopolku. Muuta halumaksi!
    static ObjectOutputStream os;

    public Tallennus(Pisteet pisteet) {
    }

    /**
     * Tallentaa annetut pisteet tiedostoon. Ensin luetaan siellä olevat pisteet
     * listaan, lisätään listaan uusi ja sitten tallennetaan koko lista.
     *
     * @param pisteet Uusi piste olio tallennettavaksi
     */
    static public void Tallenna(Pisteet pisteet) {
        ArrayList<Pisteet> oliot = new ArrayList();

        ObjectInputStream ois;
        boolean tiedosto = false;
        Object obj;
        try {
            ois = new ObjectInputStream(new FileInputStream(tallennuspolku));
            while (!tiedosto) {
                obj = null;
                try {
                    obj = ois.readObject();

                } catch (ClassNotFoundException | EOFException ex) {

                }
                if (obj != null) {
                    oliot.add((Pisteet) obj);   //luetaan kaikki aiemmat oliot listaan
                } else {
                    tiedosto = true;
                }
            }
            ois.close();
        } catch (EOFException ex) {

        } catch (IOException ex) {

        } finally {

            try {
                os = new ObjectOutputStream(new FileOutputStream(tallennuspolku));

                oliot.add(pisteet);     //lisätään uusi olio listaan
                for (Pisteet p : oliot) {

                    os.writeObject(p);      //kirjoitetaan kaikki oliot tiedostoon

                }
                os.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tallennus.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Tallennus.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
