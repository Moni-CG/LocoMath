package Entidad;

/**
 *
 * @author Fio
 */
public class Pregunta {

    private Operacion operacion;
    private Resultado resultado;
    private int primerNumero;
    private int segundoNumero;
    private String tipoOperacion;

    public Pregunta(Operacion operacion, Resultado resultado) {
        this.resultado = resultado;
        this.operacion = operacion; // obtenemos la operación aleatoria
        setPrimerNumero();
        setSegundoNumero();
        setTipoOperacion();
    }
    
    //setters and getters
    public Operacion getOperacion() {
        return operacion;
    }    

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
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
    
    

    public boolean verificarRespuesta() {
        if (resultado.getRespuesta() == operacion.getResultado()) {
            resultado.setEsCorrecta(true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Pregunta{" + primerNumero + " " + tipoOperacion + " " + segundoNumero + '}';
    }
    
    
}
