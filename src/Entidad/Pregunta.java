package Entidad;

/**
 *
 * @author Fio
 */
public class Pregunta {

    private Operacion operacion;
    private Resultado resultado;

    public Pregunta(Operacion operacion, Resultado resultado) {
        this.resultado = resultado;
        this.operacion = operacion; // obtenemos la operación aleatoria
    }

    public boolean verificarRespuesta() {
        if (resultado.getRespuesta() == operacion.getResultado()) {
            resultado.setEsCorrecta(true);
            return true;
        }
        return false;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void mostrarPregunta() {
        System.out.println("Resuelve: " + operacion.toString());
    }
}
