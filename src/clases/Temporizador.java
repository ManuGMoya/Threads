package clases;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Temporizador implements Runnable {

    private Declaraciones dec;
    private static int tiempo;

    public Temporizador(Declaraciones dec, int tiempo) {
        this.dec = dec;
        this.tiempo = tiempo;

    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < tiempo;) {
                sleep(1000);
                System.out.println("\t\tTIEMPO RESTANTE : " + tiempo);
                tiempo--;
            }
            dec.setFinTiempo(true);
            System.out.println("\n\t\tFIN DEL TIEMPO");
        } catch (InterruptedException ex) {
            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int getTiempo() {
        return tiempo;
    }

}
