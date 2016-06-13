package item;
import java.util.Random;
import personaje.*;

public class ToritoRojo extends Usable{
    private final int puntosEnergia;
    private static int count = 0;
    
    public ToritoRojo(){
        super("toritorojo" + (++count), "Proporciona energía", new Random().nextInt(6)+5);
        puntosEnergia = new Random().nextInt(41) + 10;
    }
    
    @Override
    public void usar(Personaje personaje){
        personaje.setEnergia(personaje.getEnergia() + puntosEnergia);
        personaje.setToreado(true);
    }
    
     @Override
    public String getDetalles(){
        StringBuilder cadena = new StringBuilder();
        
        cadena.append("\tTipo: Torito Rojo\n");
        cadena.append("\tAumento de energía: +").append(puntosEnergia).append("\n");
        cadena.append("Nota: tras usarlo, perderas un 10% de energía en el siguiente turno\n");
    
    return cadena.toString();
    }
}