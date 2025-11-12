package Entidad;

import java.util.Random;

/**
 *
 * @author Fio
 */
public class Operacion {

    private int primerNumero;
    private int segundoNumero;
    private String operacion;
    private int resultado;
    Random random;

    public Operacion() {
        primerNumero = 0;
        segundoNumero = 0;
        resultado = 0;
        operacion = "";
        random = new Random();
    }

    //setters y getters
    public int getPrimerNumero() {
        return primerNumero;
    }

    public void setPrimerNumero(int primerNumero) {
        this.primerNumero = primerNumero;
    }

    public int getSegundoNumero() {
        return segundoNumero;
    }

    public void setSegundoNumero(int segundoNumero) {
        this.segundoNumero = segundoNumero;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    //operaciones aritmeticas
    private int suma() {
        primerNumero = random.nextInt(100) + 1;
        segundoNumero = random.nextInt(100) + 1;
        resultado = primerNumero + segundoNumero;
        return resultado;
    }

    private int resta() {
        do {
            primerNumero = random.nextInt(100) + 1;
            segundoNumero = random.nextInt(100) + 1;
            resultado = primerNumero - segundoNumero;
        } while (resultado < 0); // esto es para evitar restas negativas

        return resultado;
    }

    private int multiplicacion() {
        primerNumero = random.nextInt(10) + 1;
        segundoNumero = random.nextInt(10) + 1;
        resultado = primerNumero * segundoNumero;
        return resultado;
    }

    public int division() {
        segundoNumero = random.nextInt(10) + 1;
        int multiplo = random.nextInt(10) + 1;
        primerNumero = segundoNumero * multiplo; //division mediante multiplos para que sea exacto
        resultado = primerNumero / segundoNumero;
        return resultado;
    }

    // verifica cual operacion se va realizar
    private void generarOperacion(String operacion) {
        
        this.operacion = operacion;

        switch (operacion) {
            case "+":
                suma();
                break;

            case "-":
                resta();
                break;

            case "x":
                multiplicacion();
                break;

            case "/":
                division();
                break;
        }
    }

    @Override
    public String toString() {
        return primerNumero + " " + operacion + " " + segundoNumero;
    }

}
