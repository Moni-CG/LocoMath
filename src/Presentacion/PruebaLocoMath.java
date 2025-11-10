package Presentacion;

import Entidad.*;

/**
 *
 * @author MONIC
 */
public class PruebaLocoMath {
    public static void main(String[] args) {
        
        Operacion operacion1 = new Operacion();
        Pregunta pregunta1 = new Pregunta(1, operacion1);
        
        System.out.println(pregunta1);
        
    }
}
