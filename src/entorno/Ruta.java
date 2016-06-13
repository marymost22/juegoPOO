package entorno;

import java.util.ArrayList;
import java.util.HashSet;

public class Ruta {
    private final ArrayList<Celda> ruta;
    private final int pasos;
    
    public Ruta(Mapa mapa, Celda origen, Celda destino){
        HashSet<Celda> visitadas = new HashSet();
        ruta = new ArrayList();
        Celda celdaAux;
        
        ruta.add(origen);
        visitadas.add(origen);
        
        do{
            celdaAux = ruta.get(ruta.size() - 1);
            celdaAux = mejorAdy(celdaAux, destino, visitadas, mapa);
            
            if(celdaAux == null){
                ruta.remove(ruta.size() - 1);
            }
            else{
                ruta.add(celdaAux);
                visitadas.add(celdaAux);
            }
        }while(!ruta.isEmpty() && (celdaAux == null || !celdaAux.equals(destino)));
        
        if(ruta.isEmpty()){
            pasos = -1;
        }
        else{
            ruta.remove(0);
            pasos = ruta.size();
        }
    }
    
    private Celda mejorAdy(Celda origen, Celda destino, HashSet<Celda> visitadas, Mapa mapa){  //Es una euristica voraz
        int distMin = Integer.MAX_VALUE;
        Celda celdaAux, celdaRes = null;
        
        for(int i = -1; i <= 1; i += 2){
            celdaAux = mapa.getCelda(origen.getCoordenadaX(), origen.getCoordenadaY() + i);
            
            if(celdaAux != null && celdaAux.isTransitable() &&
                    mapa.distanciaAndar(celdaAux, destino) < distMin && !visitadas.contains(celdaAux)){
                
                distMin = mapa.distanciaAndar(celdaAux, destino);
                celdaRes = celdaAux;
            }
            
            celdaAux = mapa.getCelda(origen.getCoordenadaX() + i, origen.getCoordenadaY());
            
            if(celdaAux != null && celdaAux.isTransitable() &&
                    mapa.distanciaAndar(celdaAux, destino) < distMin && !visitadas.contains(celdaAux)){
                
                distMin = mapa.distanciaAndar(celdaAux, destino);
                celdaRes = celdaAux;
            }
        }
        
        return celdaRes;
    }
    
    public ArrayList<Celda> getRuta(){
        return ruta;
    }
    
    public int getPasos(){
        return pasos;
    }
}