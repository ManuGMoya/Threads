package clases;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ventanas.VentanaVisualizacion;

public class Declaraciones {

    static private int contMasDeUna;
    static private int numPlazas;
    static private int tiempo, tiempoTotal, numCochesEntrados;
    static private int plazasOcupadas;
    static private boolean cerrado;
    static private boolean finTiempo;
    VentanaVisualizacion vent;
    Temporizador temp;
    ArrayList<Coche> coches = new ArrayList();

    public Declaraciones() {
        arrancarCoches();
    }

    private void arrancarCoches() {
        // AÑADIMOS UN 50% MAS DE COCHES QUE DE PLAZAS
        for (int i = 1; i <= (numPlazas * 1.5); i++) {
            coches.add(new Coche(this, i));
        }
        // ARRANCAMOS LOS COCHES
        for (Coche coche : coches) {
            new Thread(coche).start();
        }
        // ARRANCAMOS EL TEMPORIZADOR
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
        // Si el boton de Cerrar el parking de la ventana de visualizacion
        // no ha sido pulsado, permitimos al coche entrar.
        if (!VentanaVisualizacion.isCerrar()) {

            // Si no hay plazas libres...
            if (((numPlazas - plazasOcupadas) == 0)) {
                System.out.println("EL COCHE " + co.getNumCoche() + " SE PARA HASTA QUE QUEDEN PLZAS LIBRES");
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Declaraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Si hay plazas libres
            } else if (numPlazas - plazasOcupadas > 0) {
                numCochesEntrados++;
                try {
                    Thread.sleep((long) (Math.random() * 300) + 1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Declaraciones.class.getName()).log(Level.SEVERE, null, ex);
                }

                co.masVeces();
                // Si el coche entra por segunda vez al parking, se contabuliza ese coche como que ha entrado mas de una vez
                // Si ese mismo coche entra mas de 2 veces, no se contabiliza.
                if (co.getVeces() > 1 && !co.isMasDeUna()) {
                    co.setMasDeUna(true);
                    contMasDeUna++;
                }
                plazasOcupadas++;
                System.out.println("EL COCHE " + co.getNumCoche() + " ENTRA AL PARKING");
                System.out.println("PLAZAS LIBRES: " + (numPlazas - plazasOcupadas));
                System.out.println("plazas ocupadas: " + plazasOcupadas);

                // Notificamos a los coches que se pararon porque no habia plazas
                // libres para que entren en el parking
                if (numPlazas - plazasOcupadas > 0) {
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
