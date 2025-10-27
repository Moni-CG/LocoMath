package Entidad;

/**
 *
 * @author Fio
 */
public class Resultado {

    private String nombreJugador;
    private int respuesta;
    private boolean esCorrecta;

    public Resultado(String nombreJugador, int respuesta) {
        this.nombreJugador = nombreJugador;
        this.respuesta = respuesta;
        this.esCorrecta = false;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
}
