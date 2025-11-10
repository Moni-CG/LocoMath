package Entidad;

/**
 *
 * @author Moni
 */
public class Jugador {
    private int idJugador;
    private String usuario;
    private int puntaje;
    private int aciertos;
    private int errores;
    private int tiempoRestante;

    public Jugador() {
    }

    public Jugador(int idJugador, String usuario) {
        this.idJugador = idJugador;
        this.usuario = usuario;
        this.puntaje = puntaje;
        this.aciertos = aciertos;
        this.errores = errores;
        this.tiempoRestante = tiempoRestante;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }
    
    
    
    
}
