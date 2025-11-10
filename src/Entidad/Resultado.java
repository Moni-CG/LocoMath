package Entidad;

/**
 *
 * @author Fio
 */
public class Resultado {

    private int idJugador;
    private int respuesta;

    public Resultado(int IdJugador, int respuesta) {
        this.idJugador = idJugador;
        this.respuesta = respuesta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public int getRespuesta() {
        return respuesta;
    }

}
