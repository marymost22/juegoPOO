package juego;

import consola.Consola;
import consola.ConsolaNormal;
import entorno.Celda;
import entorno.Mapa;
import entorno.Mochila;
import item.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import personaje.*;

public class LoaderFile implements LoaderJuego{
    @Override
    public Juego cargarJuego(String nombreJugador, Consola consola) throws FileNotFoundException{
        Mapa mapa;
        HashSet<Enemigo> enemigos = new HashSet<>();
        Jugador jugador = null;
        ConsolaNormal consola1 = new ConsolaNormal();
        /*
        String ruta = consola1.leer("Indica la ruta: ");
        if(!ruta.endsWith("/")){
            ruta += "/";
        }*/
        String ruta = Const.ruta;
        
        
        mapa = cargarMapa(ruta+"mapa.csv");
        jugador = cargarNpcs(ruta+"npcs.csv", enemigos, mapa, nombreJugador, consola);
        if( jugador == null){
            return null;
        }
        cargarObjetos(ruta+"objetos.csv", jugador, mapa);        
        
        Juego juego = new Juego(mapa, jugador, enemigos, consola);
        
        return (juego);
    }
    
    private  Mapa cargarMapa(String archivo) throws FileNotFoundException{
        int maxX = 0, maxY = 0;
        String linea;
        String[] cords;
        Integer cordNum;
        ArrayList<Integer> coordenadas = new ArrayList();        
        Scanner lector = new Scanner(new File(archivo));
        
        while(lector.hasNext()){
            linea = lector.nextLine();
            if(linea.equals("") || linea.charAt(0) == '#') continue;
            cords = linea.split(",");
            
            cordNum = Integer.parseInt(cords[1]);
            maxX = Math.max(cordNum, maxX);
            coordenadas.add(cordNum);
            
            cordNum = Integer.parseInt(cords[0]);                       
            maxY = Math.max(cordNum, maxY);
            coordenadas.add(cordNum);
        }
        
        Mapa mapa = new Mapa(maxX+1, maxY+1);
        
        for(int i = 0; i<coordenadas.size()-1; i += 2){
            mapa.getCelda(coordenadas.get(i), coordenadas.get(i+1)).setTransitable(true);
        }
        
        return mapa;
    }
    
    public Jugador cargarNpcs(String archivo, HashSet<Enemigo> enemigos,  Mapa mapa,  String nombreJugador, Consola consola) throws FileNotFoundException{
        int cordX, cordY, salud, energia;
        Jugador jugador=null;
        String linea;
        String[] cords, campos;
        Scanner lector = new Scanner(new File(archivo));
        
        while(lector.hasNext()){
            linea = lector.nextLine();
            if(linea.equals("") || linea.charAt(0) == '#') continue;
            campos = linea.split(";");
            
            switch(campos[1]){
                case "jugador":
                    cords = campos[0].split(",");
                    cordX = Integer.parseInt(cords[1]);
                    cordY = Integer.parseInt(cords[0]);
                    salud = Integer.parseInt(campos[3]);
                    energia = Integer.parseInt(campos[4]);
                    
                    
                switch (nombreJugador){
                    case "marine":
                        jugador = new Marine(nombreJugador, cordX, cordY, salud, energia, mapa, consola);
                        break;
                        
                    case "zapador":
                        jugador = new Zapador(nombreJugador, cordX, cordY, salud, energia, mapa, consola);
                        break;
                        
                    case "francotirador":
                        jugador = new Francotirador(nombreJugador, cordX, cordY, salud, energia, mapa, consola);
                        break;
                        
                    default: 
                        return (null);
                }
    
                break;
                        
                case "enemigo":
                    cords = campos[0].split(",");
                    cordX = Integer.parseInt(cords[1]);
                    cordY = Integer.parseInt(cords[0]);
                    salud = Integer.parseInt(campos[3]);
                    energia = Integer.parseInt(campos[4]);
                    Enemigo enemigo = null;
                    
                    if(campos[2].contains("sectoid")){
                        enemigo = new Sectoid(campos[2], cordX, cordY, salud, energia, mapa, consola);
                        enemigo.setNombre(campos[2]);
                    }
                    else if (campos[2].contains("heavy_floater")){
                        enemigo = new HeavyFloater(campos[2], cordX, cordY, salud, energia, mapa, consola);
                        enemigo.setNombre(campos[2]);
                    }
                    else if (campos[2].contains("floater")){
                        enemigo = new LightFloater(campos[2], cordX, cordY, salud, energia, mapa, consola);
                        enemigo.setNombre(campos[2]);
                    }
                    if (enemigo!= null){
                        enemigos.add(enemigo);
                        enemigo.asignarCelda();
                    }
                    break;
            }
        }
        return jugador;
    }
    
    public  void cargarObjetos(String archivo, Jugador jugador, Mapa mapa) throws FileNotFoundException{
        int cordX, cordY;
        String linea;
        String[] cords, campos;
        double peso, multdano;
        int capacidad,alcance, aumentoSalud, aumentoEnergia;
        boolean manos;
        Item item;
        Celda celdaAux;
        
        Scanner lector = new Scanner(new File(archivo));
        
        while(lector.hasNext()){
            linea = lector.nextLine();
            if(linea.equals("") || linea.charAt(0) == '#') continue;
            campos = linea.split(";");
            
            switch(campos[2]){
                case "mochila":
                    capacidad = Integer.parseInt(campos[5]);
                    peso = Double.parseDouble(campos[6]);
                    Mochila mochila = new Mochila(campos[3], peso, capacidad, campos[4]); 
                    jugador.setMochila(mochila);                    
                    break;                    
                
                case "binoculares":
                    multdano = Integer.parseInt(campos[5]);
                    peso= Double.parseDouble(campos[6]);
                    cords = campos[0].split(",");
                    cordX = Integer.parseInt(cords[1]);
                    cordY = Integer.parseInt(cords[0]);
                    item = new Binoculares(campos[3], campos[4], (int) multdano, peso);
                  
                    celdaAux = mapa.getCelda(cordX, cordY);                    
                    celdaAux.addItem(item);                    
                    break;            
                                
                case "botiquin":
                    aumentoSalud = Integer.parseInt(campos[5]);
                    peso= Double.parseDouble(campos[6]);
                    cords = campos[0].split(",");
                    cordX = Integer.parseInt(cords[1]);
                    cordY = Integer.parseInt(cords[0]);
                    item = new Botiquin(campos[3], campos[4], aumentoSalud, peso);

                    celdaAux = mapa.getCelda(cordX, cordY);                    
                    celdaAux.addItem(item);                    
                    break;                 
                    
                case "arma":
                    multdano = 1 + Integer.parseInt(campos[5])/100.0;
                    alcance = Integer.parseInt(campos[6]);
                    manos = (Integer.parseInt (campos[7])>1);
                    peso = Double.parseDouble(campos[8]);
                    
                    item = new Arma(campos[3], campos[4], multdano,alcance, manos, peso); 
                    
                    cords = campos[0].split(",");
                    cordX = Integer.parseInt(cords[1]);
                    cordY = Integer.parseInt(cords[0]);
                    celdaAux = mapa.getCelda(cordX, cordY);

                switch (campos[1]) {
                    case ".":
                        celdaAux.addItem(item);
                        break;
                    case "jugador":
                        jugador.equipar(item);
                        break;
                    default:
                        Enemigo enemigoAux = celdaAux.getEnemigoNombre(campos[1]);
                        enemigoAux.equipar(item);
                        break;
                }
                break;                                        
                
                
                case "armadura":
                    multdano = 1 - Integer.parseInt(campos[5])/100.0;
                    aumentoSalud = Integer.parseInt(campos[6]);
                    aumentoEnergia = Integer.parseInt(campos[7]);
                    peso = Double.parseDouble(campos[8]);
                    
                    
                    item = new Armadura(campos[3], campos[4],multdano, aumentoSalud, aumentoEnergia, peso);
                    
                    cords = campos[0].split(",");
                    cordX = Integer.parseInt(cords[1]);
                    cordY = Integer.parseInt(cords[0]);
                    
                    celdaAux = mapa.getCelda(cordX, cordY);
                    
                    switch (campos[1]) {
                        case ".":
                            celdaAux.addItem(item);
                            break;
                        case "jugador":
                            jugador.equipar(item);
                            break;
                        default:
                            Enemigo enemigoAux = celdaAux.getEnemigoNombre(campos[1]);
                            enemigoAux.equipar(item);
                            break;
                    }
                    break;               
            }
        }
        
    }    
}