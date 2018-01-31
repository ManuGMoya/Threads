package clases;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ventanas.VentanaVisualizacion;

public class Declaraciones {

    static int contMasDeUna;
    static private int numPlazas;
    static private int tiempo, tiempoTotal, numCochesEntrados;
    static private int plazasOcupadas;
    static boolean cerrado;
    private static boolean finTiempo;
    VentanaVisualizacion vent;
    Temporizador temp;
    ArrayList<Coche> coches = new ArrayList();

    public Declaraciones(int i) {

    }

    public Declaraciones() {
        arrancarCoches();
    }

    private void arrancarCoches() {
        for (int i = 1; i <= (numPlazas * 1.5); i++) {
            coches.add(new Coche(this, i));
        }
        for (Coche coche : coches) {
            new Thread(coche).start();
        }
        temp = new Temporizador(this, tiempo);
        new Thread(temp).start();
    }

    public static int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    public static int getNumPlazas() {
        return numPlazas;
    }

    public static void setNumPlazas(int numPlazas) {
        Declaraciones.numPlazas = numPlazas;
    }

    public static void setTiempo(int tiempo) {
        Declaraciones.tiempo = tiempo;
    }

    public static void setPlazasOcupadas(int plazasOcupadas) {
        Declaraciones.plazasOcupadas = plazasOcupadas;
    }

    synchronized void entrarAparcamiento(Coche co) {

        if (!VentanaVisualizacion.isCerrar()) {
            numCochesEntrados++;
            if (((numPlazas - plazasOcupadas) == 0)) {

                System.out.println("EL COCHE " + co.getNumCoche() + " SE PARA HASTA QUE QUEDEN PLZAS LIBRES");
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Declaraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (numPlazas - plazasOcupadas > 0) {
                try {
                    Thread.sleep((long) (Math.random() * 300) + 1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Declaraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                co.masVeces();
                if (co.getVeces() > 1 && !co.isMasDeUna()) {
                    co.setMasDeUna(true);
                    contMasDeUna++;
                }
                plazasOcupadas++;
                System.out.println("EL COCHE " + co.getNumCoche() + " ENTRA AL PARKING");
                System.out.println("PLAZAS LIBRES: " + (numPlazas - plazasOcupadas));
                System.out.println("plazas ocupadas: " + plazasOcupadas);

                if (numPlazas - plazasOcupadas > 5) {
                    notifyAll();
                }
            }
        }
    }

    public int cochesMasDeUna() {
        int cont = 0;
        for (Coche co : coches) {
            if (co.isMasDeUna()) {
                cont++;
            }
        }
        return cont;
    }

    public static boolean isFinTiempo() {
        return finTiempo;
    }

    public static void setFinTiempo(boolean finTiempo) {
        Declaraciones.finTiempo = finTiempo;
    }

    public static void setTiempoTotal(int tiempoTotal) {
        Declaraciones.tiempoTotal = tiempoTotal;
    }

    public static int getTiempoTotal() {
        return tiempoTotal;
    }

    public static int getNumCochesEntrados() {
        return numCochesEntrados;
    }

    public ArrayList<Coche> getCoches() {
        return coches;
    }

    public static int getContMasDeUna() {
        return contMasDeUna;
    }

}
