package Entidad;

/**
 *
 * @author Fio
 */
public class Pregunta {
    private Operacion operacion;
    private Resultado resultado;

    public Pregunta(Resultado resultado) {
        this.resultado = resultado;
        this.operacion = new Operacion(); // genera una operación aleatoria
    }

    public boolean verificarRespuesta() {
        boolean correcta = resultado.getRespuesta() == operacion.getResultado();
        resultado.setEsCorrecta(correcta);
        return correcta;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void mostrarPregunta() {
        System.out.println("Resuelve: " + operacion.toString());
    } 
}
