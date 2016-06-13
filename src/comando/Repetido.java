package comando;

import excepcion.ComandoExcepcion;
import juego.Juego;

public class Repetido extends Compuesto implements Comando{
    private final int veces;
    
    public Repetido(Juego juego, String cmd, int rep){
        super(juego, cmd);
        veces = rep;
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        for(int i = 0; i < veces; i++){
            super.ejecutar();
        }
    }
}