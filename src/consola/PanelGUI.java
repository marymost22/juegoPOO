
package consola;

import entorno.Mapa;
import item.*;
import item.Item;
import java.util.Calendar;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import juego.Const;
import static juego.Const.notVisible;
import static juego.Const.rocas;
import static juego.Const.suelos;
import personaje.Jugador;


public abstract class PanelGUI extends JPanel{
    protected GUI gui;
        
    public PanelGUI (GUI gui){
        super();
        this.gui = gui;
    }
    
    
    public ImageIcon getImagen (Mapa mapa, int coordenadaX, int coordenadaY){
        char sprite;
        sprite = mapa.getCelda(coordenadaY, coordenadaX).getSprite();
        Random rnd = new Random();
        Jugador jugador = gui.getJuego().getJugador();
        
        switch (sprite){
            
            case '@':
                switch(jugador.getNombre()){
                    case "zapador":
                        return new ImageIcon(getClass().getResource(Const.zapadorMapa));
                    case "marine":
                        return new ImageIcon(getClass().getResource(Const.marineMapa));
                    case "francotirador":
                        return new ImageIcon(getClass().getResource(Const.francotiradorMapa));
                }

            case 'X':
                return new ImageIcon(getClass().getResource(Const.enemigo));
                
            case '#':
                return new ImageIcon(getClass().getResource(rocas[rnd.nextInt(1)]));
                
            case '*':
                return new ImageIcon(getClass().getResource(notVisible));
                            
            case 'O':
                return new ImageIcon(getClass().getResource(suelos[rnd.nextInt(1)]));
                
            default:
                return new ImageIcon(getClass().getResource(suelos[rnd.nextInt(1)]));
        }
    }
    
    public ImageIcon getImagen (Item item){
        
         if (item == null) 
                return new ImageIcon(getClass().getResource(Const.casillaVacia));
        
        else if (item instanceof Arma)
                return new ImageIcon(getClass().getResource(Const.casillaArma2));
        
        else if (item instanceof Armadura)
                return new ImageIcon(getClass().getResource(Const.casillaArmadura));
        
        else if (item instanceof Binoculares)
                return new ImageIcon(getClass().getResource(Const.casillaBinoculares));
            
        else if (item instanceof Botiquin)
            return new ImageIcon(getClass().getResource(Const.casillaBotiquin));
        
        else if (item instanceof Explosivo)
            return new ImageIcon(getClass().getResource(Const.casillaArma1));
                            
        else if (item instanceof ToritoRojo)
            return new ImageIcon(getClass().getResource(Const.casillaRedbull));
                
        else 
                return new ImageIcon(getClass().getResource(Const.casillaVacia));
                
    }
     
    public ImageIcon getImagen ( String imagen ){
         
        if (imagen == null) 
            return new ImageIcon(getClass().getResource(Const.vacio));
        
        else if ( "salud".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.salud));
     
        else if ( "toritoRojo".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.toritoRojo));
     
        else if ( "arma1".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.arma1));
     
        else if ( "arma2".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.arma2));
        
        else if ( "armadura".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.armadura));
     
        else if ( "binoculares".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.binoculares));
      
        else if ( "NoToritoRojo".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.noToritoRojo));
     
        else if ( "NoArma1".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.noArma1));
     
        else if ( "NoArma2".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.noArma2));
        
        else if ( "NoArmadura".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.noArmadura));
     
        else if ( "NoBinoculares".equals(imagen))
            return new ImageIcon(getClass().getResource(Const.noBinoculares));
        else 
            return new ImageIcon(getClass().getResource(Const.vacio));
     }
    
    public ImageIcon getImagen (Jugador jugador){
        
        int salud = jugador.getSalud();
        
        if ( jugador.getNombre().equals("marine") && salud>50){ 
            return new ImageIcon(getClass().getResource(Const.marine));
        }
        else if ( jugador.getNombre().equals("marine") && salud<=50){ 
            return new ImageIcon(getClass().getResource(Const.marineVida));
        }
        else if ( jugador.getNombre().equals("francotirador") && salud>50){ 
            return new ImageIcon(getClass().getResource(Const.francotirador));
        }
        else if ( jugador.getNombre().equals("francotirador") && salud<=50){ 
            return new ImageIcon(getClass().getResource(Const.francotiradorVida));
        }
        else if ( jugador.getNombre().equals("zapador") && salud>50){ 
            return new ImageIcon(getClass().getResource(Const.zapador));
        }
        else if ( jugador.getNombre().equals("zapador") && salud<=50){ 
            return new ImageIcon(getClass().getResource(Const.zapadorVida));
        }
        else{
            return new ImageIcon(getClass().getResource(Const.casillaVacia));
        }
    }
    

    
    
    public String cadenaHora(){
        
        Calendar calendario = Calendar.getInstance();
        int horas, minutos, segundos;
        
        horas = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
    
        
        return  new StringBuilder().append(horas + ":" + minutos + ":" + segundos).toString();
    }
    
}


