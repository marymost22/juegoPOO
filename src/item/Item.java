package item;

import excepcion.ItemNoUsable;
import java.util.Objects;
import personaje.Personaje;

public abstract class Item {    
    private final String nombre;
    private final String descripcion;
    private final double peso;
    
    public abstract void usar(Personaje personaje) throws ItemNoUsable;
    
    public Item(String nom, String desc, double pes){
        nombre = nom;
        descripcion = desc;
        peso = pes;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getPeso(){
        return peso;
    }
    
    public String getDetalles(){
        StringBuilder cadena = new StringBuilder();
        
        cadena.append(nombre).append("\n");
        cadena.append("\tDescripción: ").append(descripcion).append("\n");
        cadena.append("\tPeso: ").append(peso).append("\n");
        
        return cadena.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        return Objects.equals(this.nombre, other.nombre);
    }
}