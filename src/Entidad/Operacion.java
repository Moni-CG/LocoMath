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
        generarOperacionRandom();
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
        operacion = "+";
        primerNumero = random.nextInt(100) + 1;
        segundoNumero = random.nextInt(100) + 1;
        resultado = primerNumero + segundoNumero;
        return resultado;
    }

    private int resta() {
        operacion = "-";
        do {
            primerNumero = random.nextInt(100) + 1;
            segundoNumero = random.nextInt(100) + 1;
            resultado = primerNumero - segundoNumero;
        } while (resultado < 0); // esto es para evitar restas negativas

        return resultado;
    }

    private int multiplicacion() {
        operacion = "×";
        primerNumero = random.nextInt(10) + 1;
        segundoNumero = random.nextInt(10) + 1;
        resultado = primerNumero * segundoNumero;
        return resultado;
    }

    public int division() {
        operacion = "÷";
        segundoNumero = random.nextInt(10) + 1;
        int multiplo = random.nextInt(10) + 1;
        primerNumero = segundoNumero * multiplo; //division mediante multiplos para que sea exacto
        resultado = primerNumero / segundoNumero;
        return resultado;
    }

    // verifica cual operacion se va realizar
    private void generarOperacionRandom() {

        int tipo = random.nextInt(4); // 0 = suma, 1 = resta, 2 = multiplicación, 3 = división

        switch (tipo) {
            case 0:
                suma();
                break;

            case 1:
                resta();
                break;

            case 2:
                multiplicacion();
                break;

            case 3:
                division();
                break;
        }
    }

    @Override
    public String toString() {
        return primerNumero + " " + operacion + " " + segundoNumero;
    }

}
