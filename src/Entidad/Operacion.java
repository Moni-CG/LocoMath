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

    public Operacion() {
        generarOperacionRandom();
    }

    // verifica cual operacion se va realizar
    private void generarOperacionRandom() {
        Random rand = new Random();
        int tipo = rand.nextInt(4); // 0 = suma, 1 = resta, 2 = multiplicación, 3 = división

        switch (tipo) {
            case 0:
                operacion = "+";
                primerNumero = rand.nextInt(100) + 1;
                segundoNumero = rand.nextInt(100) + 1;
                resultado = primerNumero + segundoNumero;
                break;

            case 1:
                operacion = "-";
                primerNumero = rand.nextInt(100) + 1;
                segundoNumero = rand.nextInt(primerNumero) + 1; // evita negativos
                resultado = primerNumero - segundoNumero;
                break;

            case 2:
                operacion = "×";
                primerNumero = rand.nextInt(10) + 1;
                segundoNumero = rand.nextInt(10) + 1;
                resultado = primerNumero * segundoNumero;
                break;

            case 3:
                operacion = "÷";
                segundoNumero = rand.nextInt(9) + 1; // evita dividir por 0
                int multiplo = rand.nextInt(10) + 1;
                primerNumero = segundoNumero * multiplo; // asegura división exacta
                resultado = primerNumero / segundoNumero;
                break;
        }
    }

    public int getPrimerNumero() {
        return primerNumero;
    }

    public int getSegundoNumero() {
        return segundoNumero;
    }

    public String getOperacion() {
        return operacion;
    }

    public int getResultado() {
        return resultado;
    }

    @Override
    public String toString() {
        return primerNumero + " " + operacion + " " + segundoNumero;
    }

}
