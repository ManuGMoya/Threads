package clases;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Coche implements Runnable {

    private Declaraciones decl;
    private int numCoche, veces;
    private boolean masDeUna;

    public Coche(Declaraciones decl, int num) {
        this.decl = decl;
        numCoche = num;
    }

    @Override
    public void run() {
        while (!decl.isFinTiempo()) {
            decl.entrarAparcamiento(this);
            try {
                int t = 0;
                if (veces > 0) {
                    t = tiempoAleatorio();
                    Thread.sleep(t * 1000);
                    System.out.println("EL COCHE " + numCoche + " SALE DEL PARKING");
                    Declaraciones.setPlazasOcupadas(Declaraciones.getPlazasOcupadas() - 1);
                    Thread.sleep(1000);
                }
                Declaraciones.setTiempoTotal(Declaraciones.getTiempoTotal() + t);
            } catch (InterruptedException ex) {
                Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int tiempoAleatorio() {
        return (int) (Math.random() * 15) + 5;
    }

    public int getNumCoche() {
        return numCoche;
    }

    public boolean isMasDeUna() {
        return masDeUna;
    }

    public void setMasDeUna(boolean masDeUna) {
        this.masDeUna = masDeUna;
    }

    public void masVeces() {
        veces++;
    }

    public int getVeces() {
        return veces;
    }

}
