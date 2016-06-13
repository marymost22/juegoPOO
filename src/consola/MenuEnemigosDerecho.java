/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import comando.Mirar;
import entorno.Celda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import juego.Juego;
import personaje.Enemigo;

/**
 *
 * @author maluz_000
 */
public class MenuEnemigosDerecho extends Menus{
    
    
    public MenuEnemigosDerecho(Juego juego, GUI gui, Celda celda){
        super("Enemigos", gui);
 
        if(celda==null){
            System.out.println("Error: Menú enemigos");
        }
        
        for (Enemigo e : celda.getEnemigos()){
                JMenuItem item = new JMenuItem(e.getNombre());

                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {

                    cmd = new Mirar(juego.getJugador(), celda, celda.getEnemigoNombre(e.getNombre()));
                    if (cmd!=null) run();
                    }
                });
                this.add(item);
            }
    }
    
    
}