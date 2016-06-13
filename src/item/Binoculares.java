package item;
import java.util.Random;
import personaje.*;

public class Binoculares extends Equipable{
    private final int aumentoVision;
    private static int count = 0;
    
    public Binoculares(){
        super("binoculares" + (++count), "Aumentan el rango de visión", new Random().nextInt(6)+5);
        aumentoVision = new Random().nextInt(9) + 2;
    }
    
    public Binoculares(String nombre, String descripcion, int alcance, double peso){
        super(nombre, descripcion, peso);      
        aumentoVision = alcance;
    }
    
    @Override
    public void usar(Personaje personaje){
        personaje.equipar(this);
        if(personaje instanceof Jugador){
            personaje.setBinoculares(true);
        }
    }

    public int getAumentoVision() {
        return aumentoVision;
    }

 @Override
    public String getDetalles(){
        StringBuilder cadena = new StringBuilder();
        
        cadena.append("\tTipo: Binoculares\n");
        cadena.append("\tAlcance: ").append(aumentoVision).append("\n");
    
        return cadena.toString();
    }

}
