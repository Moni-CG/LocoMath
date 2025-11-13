package Entidad;

/**
 *
 * @author Fio
 */
public class Pregunta {

    private int idPregunta;
    private Operacion operacion;
    private int primerNumero;
    private int segundoNumero;
    private String tipoOperacion;
    private int resultado;

    public Pregunta(int idPregunta) {
        this.idPregunta = idPregunta;
        this.operacion = new Operacion();
    }

    //setters and getters
    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public int getPrimerNumero() {
        return primerNumero;
    }

    public void setPrimerNumero() {
        this.primerNumero = operacion.getPrimerNumero();
    }

    public int getSegundoNumero() {
        return segundoNumero;
    }

    public void setSegundoNumero() {
        this.segundoNumero = operacion.getSegundoNumero();
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion() {
        this.tipoOperacion = operacion.getOperacion();
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado() {
        this.resultado = operacion.getResultado();
    }

    public void generarOperacion(String tipoOperacion) {
        operacion.generarOperacion(tipoOperacion);
        setPrimerNumero();
        setSegundoNumero();
        setTipoOperacion();
        setResultado();
    }

    @Override
    public String toString() {
        return primerNumero + " " + tipoOperacion + " " + segundoNumero;
    }

}
