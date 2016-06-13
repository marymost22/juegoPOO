package juego;

import java.util.ArrayList;
import java.util.Arrays;

public class Acciones {
    private int maxAcciones;
    private int numAcciones;
    private final ArrayList<String> acciones;
    
    public Acciones(int max){
        maxAcciones = max;
        numAcciones = 0;
        acciones = new ArrayList();
    }
    
    public void nuevaAccion(String accion){
        String[] serieAcciones = accion.split("\n");
        
        numAcciones += serieAcciones.length;
        
        if(numAcciones > maxAcciones){
            for(int i = 0; i < (numAcciones - maxAcciones); i++){
                acciones.remove(0);
            }
            
            numAcciones = maxAcciones;
        }
        
        acciones.addAll(Arrays.asList(serieAcciones));
    }
    
    public String getAccion(int i){
        return acciones.get(i);
    }
    
    public int getNumAcciones(){
        return numAcciones;
    }
    
    public void setMaxAcciones(int max){
        maxAcciones = max;
    }
    
    public String accionesToString(){
        StringBuilder cadena = new StringBuilder();
        
    
        for(int i =0; i<maxAcciones; i++){
            cadena.append(acciones.get(i)).append("\n");
        }
        
        return cadena.toString();
    }
}