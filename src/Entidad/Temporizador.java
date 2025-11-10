package Entidad;

/**
 *
 * @author Moni
 */
public class Temporizador implements Runnable  {
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
        if (enCurso) return;
        enCurso = true;
        hilo = new Thread(this);
        hilo.start();
    }

    public void detener() {
        enCurso = false;
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    @Override
    public void run() {
        try {
            while (enCurso && tiempoRestante > 0) {
                System.out.println("Tiempo restante: " + tiempoRestante + "s");
                Thread.sleep(1000);
                tiempoRestante--;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (tiempoRestante <= 0) {
            enCurso = false;
            System.out.println("¡Se agotó el tiempo!");
        }
    }
    
}
