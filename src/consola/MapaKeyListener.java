/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import comando.Comando;
import comando.Mover;
import comando.Pasar;
import entorno.Celda;
import entorno.Mapa;
import excepcion.CeldaNoValida;
import excepcion.ComandoExcepcion;
import excepcion.InsuficienteEnergia;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import javax.swing.ImageIcon;
import juego.Juego;
import personaje.Jugador;

/**
 *
 * @author maluz_000
 */
public class MapaKeyListener extends  KeyAdapter {
    private Mapa mapa;
    private Jugador jugador;
    private Juego juego;
    private Comando cmd;
    private Celda celdaDestino;
    private PanelIzquierdo panelI;
    private PanelMapa panelM;
    private GUI gui;
    
    public MapaKeyListener(Juego juego, GUI gui){
        super();
        this.gui=gui;
        this.juego = juego;
        this.mapa = juego.getMapa();
        this.jugador = juego.getJugador();
        cmd = null;
        panelI = gui.getPanelIzquierdo();
        panelM = gui.getPanelMapa();
    }
        @Override
    public void keyReleased(KeyEvent evento)  {
                                       
        switch(evento.getKeyCode()){
          
            case  VK_LEFT:
                celdaDestino = mapa.getCelda(jugador.getCeldaActual().getCoordenadaX()-1, jugador.getCeldaActual().getCoordenadaY());
                cmd =  new Mover(jugador, celdaDestino);
                break;
            case VK_RIGHT:
                celdaDestino = mapa.getCelda(jugador.getCeldaActual().getCoordenadaX()+1, jugador.getCeldaActual().getCoordenadaY());
                cmd =  new Mover(jugador, celdaDestino);
                break;
            case VK_UP:
                celdaDestino = mapa.getCelda(jugador.getCeldaActual().getCoordenadaX(), jugador.getCeldaActual().getCoordenadaY()-1);
                cmd =  new Mover(jugador, celdaDestino);
                break;
            case VK_DOWN:
                celdaDestino = mapa.getCelda(jugador.getCeldaActual().getCoordenadaX(), jugador.getCeldaActual().getCoordenadaY()+1);
                cmd =  new Mover(jugador, celdaDestino);
                break;
            case VK_ENTER:
                cmd = new Pasar(juego, juego.getEnemigos());
                gui.getPanelIzquierdo().updateActividad("Nuevo turno!");
                break;
                
        }
        if (cmd!=null){
            run();
        }
    }
    
    public void run  (){
        try {
            cmd.ejecutar();
            gui.getPanelEstado().updatePersonaje();
            panelM.updateGraphMatrix();
        }   
        catch (CeldaNoValida ex) {
            panelI.updateActividad("Celda no válida");
        }
        catch(InsuficienteEnergia ex){
            panelI.updateActividad("No tienes energia suficiente");
        }
        catch(ComandoExcepcion ex){
            panelI.updateActividad("Error al procesar el comando");
        }   
    }
}

