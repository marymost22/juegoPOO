package item;
import personaje.*;
import excepcion.ItemNoUsable; 
import java.text.DecimalFormat;
import java.util.Random;

public class Armadura extends Equipable{
    private final double multDano;
    private final int aumentoSalud;
    private final int aumentoEnergia;
    private static int count = 0;
    
    public Armadura(){
        super("armadura" + (++count), "Proporciona defensa", new Random().nextInt(6)+5);
        
        Random rnd = new Random();
        
        aumentoSalud = rnd.nextInt(16) + 5;
        aumentoEnergia = rnd.nextInt(16) + 5;
        multDano = rnd.nextDouble()*0.4 + 0.5;
    }
    
    public Armadura(String nombre, String descripcion, double multDano, int aumentoSalud,int aumentoEnergia, double peso){
        super(nombre, descripcion, peso);
        this.aumentoEnergia = aumentoEnergia;
        this.aumentoSalud = aumentoSalud;
        this.multDano = multDano;    
    }
    
    @Override
    public void usar(Personaje personaje) throws ItemNoUsable{
        throw new ItemNoUsable();
    }

    public double getMultDano() {
        return multDano;
    }

    public int getAumentoSalud() {
        return aumentoSalud;
    }

    public int getAumentoEnergia() {
        return aumentoEnergia;
    }    
    
     @Override
    public String getDetalles(){
        String dano = String.format("%.02f", multDano);
        StringBuilder cadena = new StringBuilder();
        
        cadena.append(super.getDetalles());        
        cadena.append("\tTipo: Armadura\n");                
        cadena.append("\tMultiplicador de daño: ").append((dano)).append("\n");
        cadena.append("\tAumento de salud: +").append(aumentoSalud).append("\n");
        cadena.append("\tAumento de energía: +").append(aumentoEnergia).append("\n");
    
        return cadena.toString();
    }    
} 