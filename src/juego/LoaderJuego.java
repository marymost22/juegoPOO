package juego;

import consola.Consola;
import java.io.FileNotFoundException;

public interface LoaderJuego {
    public Juego cargarJuego(String nombreJugador, Consola consola) throws FileNotFoundException;
}