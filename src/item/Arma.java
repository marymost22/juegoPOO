package item;

import excepcion.ItemNoUsable;
import java.util.Random;
import personaje.*;


public class Arma extends Equipable{
    private final double multDano;
    private final int alcance;
    private final boolean dosManos;
    private static int count = 0;
    
    public Arma(){
        super("arma" + (++count), "Sirve para atacar", new Random().nextInt(6)+5);
        
        Random rnd = new Random();
        
        alcance = rnd.nextInt(9) + 2;
        dosManos = rnd.nextInt(3) == 0;
        multDano = rnd.nextDouble()*2.5 + 1.5;
    }
    
    public Arma(String nombre, String descripcion, double multDano, int alcance, boolean dosManos, double peso){
        super(nombre, descripcion, peso);
        this.alcance = alcance;
        this.dosManos = dosManos;
        this.multDano = multDano;    
    }
    
    @Override
    public void usar(Personaje personaje) throws ItemNoUsable {
        throw new ItemNoUsable();
    }

    public boolean isDosManos() {
        return dosManos;
    }

    public int getAlcance() {
        return alcance;
    }

    public double getMultDano() {
        return multDano;
    }
    
    @Override
    public String getDetalles(){
        StringBuilder cadena = new StringBuilder();
        
        cadena.append(super.getDetalles());        
        cadena.append("\tTipo: Arma ");
        if(isDosManos()) cadena.append("a dos manos\n");
        else cadena.append("a una mano\n");
        cadena.append("\tAlcance: ").append(alcance).append("\n");
        cadena.append("\tMultiplicador de daño: ").append(multDano).append("\n");
        
        return cadena.toString();
    }
}