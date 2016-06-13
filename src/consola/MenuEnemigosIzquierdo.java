/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import comando.Atacar;
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
public class MenuEnemigosIzquierdo extends Menus {

    public MenuEnemigosIzquierdo(Juego juego, GUI gui, Celda celda){
        
        super("Atacar", gui);
        
        for (Enemigo e : celda.getEnemigos()){
            JMenuItem item = new JMenuItem(e.getNombre());

            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {

                    cmd = new Atacar(juego, celda, e);
                    if (cmd!=null){ 
                        run();
                        gui.getPanelMapa().updateGraphMatrix();
                    }
                }
            });
            this.add(item);
        }
    }
}
        
    