package Entidad;

/**
 *
 * @author Moni
 */
public class Temporizador implements Runnable {

    private int duracion;
    private int tiempoRestante;
    private boolean enCurso;
    private Thread hilo;

    public Temporizador(int duracion) {
        this.duracion = duracion;
        this.tiempoRestante = duracion;
        this.enCurso = false;
    }

    public void iniciar() {
        if (enCurso) {
            return;
        }
        enCurso = true;
        hilo = new Thread(this);
        hilo.start();
    }

    public void detener() {
        enCurso = false;
        if (hilo != null && hilo.isAlive()) {
            hilo.interrupt();
        }
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public boolean isTiempoAgotado() {
        return tiempoRestante <= 0;
    }

    public int getDuracion() {
        return duracion;
    }

    public void reiniciar() {
        detener(); // detiene hilo previo
        tiempoRestante = duracion;
        enCurso = true;
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        try {
            while (enCurso && tiempoRestante > 0) {
                Thread.sleep(1000);
                tiempoRestante--;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (isTiempoAgotado()) {
            detener();
        }
    }

}
