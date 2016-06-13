/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import comando.Comando;
import comando.Inventario;
import comando.Mirar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import juego.Juego;
import personaje.Jugador;

/**
 *
 * @author maluz_000
 */
public class MenuJugador extends Menus{
    
    
    public MenuJugador(Juego juego, GUI gui){    
        super(juego.getJugador().getNombre(), gui);
    
        JMenuItem detalles = new JMenuItem("Detalles");
        detalles.addActionListener(new ActionListener() {  
                @Override
                public void actionPerformed(ActionEvent event) {

                cmd = new Inventario(juego.getJugador());
                if (cmd!=null) run();
                }
            });
        this.add(detalles);
    }
}
        
