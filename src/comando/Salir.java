package comando;

import juego.Juego;

public class Salir implements Comando{
    private final Juego juego;
    
    public Salir(Juego juego){
        this.juego = juego;
    }
    
    @Override
    public void ejecutar(){
        juego.perderJuego();
    }    
}