package Entidad;

/**
 *
 * @author Moni
 */
public class Ronda {
    private int idRonda;
    private Pregunta pregunta;
    private Jugador[] jugadores;
    private boolean finalizada;
    private Temporizador temporizador;
    private boolean[] jugadoresRespondieron;

    public Ronda(int idRonda, Jugador[] jugadores, int duracionSegundos) {
        this.idRonda = idRonda;
        this.jugadores = jugadores;
        this.pregunta = new Pregunta(idRonda, new Operacion());
        this.temporizador = new Temporizador(duracionSegundos);
        this.finalizada = false;
        this.jugadoresRespondieron = new boolean[jugadores.length];
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void iniciarRonda() {
        System.out.println("===Ronda " + idRonda + " ===");
        System.out.println("Pregunta: " + pregunta.toString());
        temporizador.iniciar();
    }

    public void responder(Jugador jugador, Resultado respuesta) {
        if (finalizada || !temporizador.isEnCurso()) {
            System.out.println("Tiempo terminado o ronda finalizada. No se puede responder.");
            return;
        }

        int index = obtenerIndiceJugador(jugador);
        if (index == -1) {
            System.out.println("Jugador no pertenece a esta ronda.");
            return;
        }

        if (jugadoresRespondieron[index]) {
            System.out.println(jugador.getUsuario() + " ya respondió esta ronda.");
            return;
        }

        boolean correcto = pregunta.verificarRespuesta(respuesta);

        if (correcto) {
            jugador.setAciertos(jugador.getAciertos() + 1);
            jugador.setPuntaje(jugador.getPuntaje() + 10); 
            System.out.println("✅ " + jugador.getUsuario() + " respondió correctamente!");
        } else {
            jugador.setErrores(jugador.getErrores() + 1);
            jugador.setPuntaje(Math.max(0, jugador.getPuntaje() - 5)); // -5 por error
            System.out.println("❌ " + jugador.getUsuario() + " falló.");
        }

        jugadoresRespondieron[index] = true;

        if (todosRespondieron()) {
            finalizarRonda();
        }
    }

    private int obtenerIndiceJugador(Jugador jugador) {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getIdJugador() == jugador.getIdJugador()) {
                return i;
            }
        }
        return -1;
    }

    private boolean todosRespondieron() {
        for (boolean responded : jugadoresRespondieron) {
            if (!responded) {
                return false;
            }
        }
        return true;
    }

    public void finalizarRonda() {
        finalizada = true;
        temporizador.detener();
        System.out.println("Ronda " + idRonda + " finalizada.");
    }

    public String mostrarIdRonda() {
        return "Ronda #" + idRonda;
    }   
}

