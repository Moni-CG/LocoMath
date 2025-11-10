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

    public Pregunta(int idPregunta, Operacion operacion) {
        this.idPregunta = idPregunta;
        this.operacion = operacion; // obtenemos la operación aleatoria
        setPrimerNumero();
        setSegundoNumero();
        setTipoOperacion();
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

    public boolean verificarRespuesta(int respuesta) {
        if (respuesta == operacion.getResultado()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Pregunta " + idPregunta + "(" + primerNumero + " " + tipoOperacion + " " + segundoNumero + '}';
    }
    
    
}
