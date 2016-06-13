package proyecto_v4;

import comando.Comando;
import consola.GUI;
import consola.*;
import java.io.FileNotFoundException;
import juego.Juego;
import juego.LoaderDefault;
import juego.LoaderFile;
import juego.LoaderJuego;


public class Proyecto_v4 {
    public static void main(String[] args) {       
        Juego bigBang = null;
        LoaderJuego loader = null;
        ConsolaNormal consola = new ConsolaNormal();
       
        String nombreJugador;
        
        GUI interfaz = new GUI();
        
        for(int i=0; i<50; i++) consola.imprimirln("");
        
        do{
            String opcion = consola.leer("Modo de juego:\n"
                + "\ta) Aleatorio\n"
                + "\tb) Mapa predeterminado\n"
                + "\ts) Salir"
                + "\nOpción: ");
       
            switch (opcion){
                case "a":
                    loader = new LoaderDefault();
                    break;
                case "b":
                    loader = new LoaderFile();
                    break;
                case "s": 
                    System.exit(0);
            }
        }while(loader == null);
        
        do{
            nombreJugador = consola.leer("Indica el personaje con el que quieres jugar: ");
            try{
                bigBang = loader.cargarJuego(nombreJugador.toLowerCase(), interfaz);
            }
            catch (FileNotFoundException ex) {
                consola.imprimirln("No se ha encontrado el archivo");
                System.exit(0);
            }
        }while (bigBang == null);
        
        interfaz = new GUI((bigBang));
        bigBang.setConsola(interfaz);
        
    }
}