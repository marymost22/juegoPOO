package item;
import java.util.Random;
import personaje.*;

public class Botiquin extends Usable {
    private final int puntosSalud;
    private static int count  = 0;
    
    public Botiquin(){
        super("botiquin" + (++count), "Sirve para curarte", new Random().nextInt(6)+5);
        puntosSalud = new Random().nextInt(41) + 10;
    }
    
    public Botiquin(String nombre, String descripcion, int saludRecuperada, double peso){
        super(nombre, descripcion, peso);      
        puntosSalud = saludRecuperada;
    }

    @Override
    public void usar(Personaje personaje) {
        personaje.setSalud(personaje.getSalud()+ puntosSalud);
    }
    
    @Override
    public String getDetalles(){
        StringBuilder cadena = new StringBuilder();
    
        cadena.append("\tTipo: Botiquín\n");
        cadena.append("\tSalud: +").append(puntosSalud).append("\n");
        
        return cadena.toString();
    }
}