package comando;

import consola.ConsolaNormal;
import juego.Juego;

public class Ayuda implements Comando{
    private final Juego juego;
    
    public Ayuda(Juego juego){
        this.juego = juego;
    }
        
    @Override
    public void ejecutar() {
        juego.ayuda();
        new ConsolaNormal().leer("Pulsa ENTER para continuar");
    }
}