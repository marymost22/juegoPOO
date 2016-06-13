package juego;
import consola.Consola;
import consola.ConsolaNormal;
import consola.Final;
import consola.GUI;
import personaje.*;
import entorno.Mapa;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Juego {
    
    private Consola consola;
    private final Jugador jugador;
    private Mapa mapa;
    private HashSet<Enemigo> enemigos;
    
    public Juego(Mapa mapa, Jugador jugador, HashSet<Enemigo> enemigos, Consola consola){
        if(mapa == null || jugador == null || enemigos == null){
            consola.imprimir(" Juego (): parámetro no válido");
            System.exit(0);
        }
        
        this.consola = consola;
        this.mapa = mapa;
        this.jugador = jugador;
        this.enemigos = enemigos;
    }
    
    public void setConsola(Consola consola){
        if(consola!=null){
            this.consola = consola;
            jugador.setConsola(consola);
            for(Enemigo e: enemigos){
                e.setConsola(consola);
            }
        }
            
    }
/*
    public Juego(boolean cargarFicheros){
        this.jugador=null;
        Loader loader;
        ConsolaNormal consola = new ConsolaNormal();
        
        String nombreJugador = consola.leer("Indica qué personaje quieres ser: ");
        
        if(cargarFicheros){
            String ruta = consola.leer("Indica la ruta: ");
            if(!ruta.endsWith("/")){
                ruta += "/";
            }
            loader = new LoaderFile(ruta);
        }
        else{
            loader = new LoaderDefault();
        }   
        
        loader.cargarJuego(jugador, mapa, enemigos, nombreJugador);
    }    
  */  
    public Mapa getMapa() {
        return mapa;
    }

    public HashSet<Enemigo> getEnemigos() {
        return enemigos;
    }

    public Jugador getJugador() {
        return jugador;
    }
    
    public void ganarJuego(){
     if(consola instanceof ConsolaNormal){
        consola.imprimir( "/\\ \\ / /  /\\ \\   /\\  ___\\   /\\__  _\\ /\\  __ \\   /\\  == \\   /\\ \\   /\\  __ \\   \n" +
            "\\ \\ \\'/   \\ \\ \\  \\ \\ \\____  \\/_/\\ \\/ \\ \\ \\/\\ \\  \\ \\  __<   \\ \\ \\  \\ \\  __ \\  \n" +
            " \\ \\__|    \\ \\_\\  \\ \\_____\\    \\ \\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\  \\ \\_\\ \\_\\ \n" +
            "  \\/_/      \\/_/   \\/_____/     \\/_/   \\/_____/   \\/_/ /_/   \\/_/   \\/_/\\/_/ \n" +
            "                                                                             ");
        
        System.exit(0); }
     else if (consola instanceof GUI){
         consola.cerrar();
         Final ganar = new Final((GUI) consola,true);
     }
    }
    
    public void perderJuego(){
        if(consola instanceof ConsolaNormal){
            consola.imprimir("┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼\n" +
                            "┼┼██▀▀█┼█▀▀██┼█▀█▄█▀█┼█▀▀┼\n" +
                            "┼┼█┼┼┼█┼█┼┼┼█┼█┼┼█┼┼█┼█┼┼┼\n" +
                            "┼┼█┼┼▄▄┼█▄▄▄█┼█┼┼▀┼┼█┼█▀▀┼\n" +
                            "┼┼█┼┼┼█┼█┼┼┼█┼█┼┼┼┼┼█┼█┼┼┼\n" +
                            "┼┼██▄▄█┼█┼┼┼█┼█┼┼┼┼┼█┼█▄▄┼\n" +
                            "┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼\n" +
                            "┼┼█▀▀██┼▀█┼┼█▀┼██▀▀┼█▀▀█▄┼\n" +
                            "┼┼█┼┼██┼┼█┼┼█┼┼█┼┼┼┼█┼┼┼█┼\n" +
                            "┼┼█┼┼██┼┼█┼┼█┼┼█▀▀▀┼█▄▄▄▀┼\n" +
                            "┼┼█┼┼██┼┼█┼█▀┼┼█┼┼┼┼█┼┼┼█┼\n" +
                            "┼┼█▄▄██┼┼─█▀┼┼┼██▄▄┼█┼┼┼█┼\n" +
                            "┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼██┼┼\n" +
                            "┼┼┼████▄┼┼┼▄▄▄▄▄┼┼┼▄████┼┼\n" +
                            "┼┼┼┼┼┼▀▀█▄███████▄█▀▀┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼┼███████████┼┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼┼██▀▀▀█▀▀▀██┼┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼┼██┼┼┼█┼┼┼██┼┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼┼████▀▄▀████┼┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼┼┼█████████┼┼┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼▄▄█┼┼█▀█▀█┼┼█▄▄┼┼┼┼┼\n" +
                            "┼┼┼┼┼▀▀██┼┼┼┼┼┼┼┼┼██▀▀┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼\n" +
                            "┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼\n");
            System.exit(0);
        }
        else if (consola instanceof GUI){
            consola.cerrar();
             Final perder = new Final((GUI) consola,false);
        }
    }
    
    
    public String frasePrincipal() {
        return(jugador.getNombre()+" - salud["+ jugador.getSalud() + "] energía[" + jugador.getEnergia() + "]► ");
    }
    
    public void ayuda() {
        Scanner lector;
        try {
            lector = new Scanner(new File("ayuda.txt"));
        }
        catch (FileNotFoundException ex) {
            jugador.getAcciones().nuevaAccion("No se encuentra el fichero de ayuda");
            mapa.imprimirMapa(jugador.getAcciones());
            return;
        }
        ConsolaNormal consola = new ConsolaNormal();
        String linea;
        
        while(lector.hasNext()){
            linea = lector.nextLine();    
            consola.imprimirln(linea);
        }
    }
}