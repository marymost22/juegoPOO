/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import juego.Const;
import personaje.Jugador;

/**
 *
 * @author maluz_000
 */
public class Final extends JFrame{

    public Final(GUI gui, boolean valor)  {
        super();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        JLabel imagen = new JLabel();
        
        this.setLayout(new BorderLayout());
        
        panel.setVisible(true);
        panel.setOpaque(true);
        
        imagen.setVisible(true);
        imagen.setOpaque(true);
        
        ImageIcon icono = new ImageIcon(getImagen(gui.getJuego().getJugador(), valor).getImage());  
        imagen.setIcon(icono); 
        
        panel.add(imagen);
        this.getContentPane().add(panel);
        
        pack();
        
        this.setVisible(true);
        
        
    }
    
    private ImageIcon getImagen (Jugador jugador, boolean valor){
        if ( jugador.getNombre().equals("marine") && valor){ 
            return new ImageIcon(getClass().getResource(Const.marineGanar));
        }
        else if ( jugador.getNombre().equals("marine") && !valor){ 
            return new ImageIcon(getClass().getResource(Const.marinePerder));
        }
        else if ( jugador.getNombre().equals("francotirador") && valor){ 
            return new ImageIcon(getClass().getResource(Const.francotiradorGanar));
        }
        else if ( jugador.getNombre().equals("francotirador") && !valor){ 
            return new ImageIcon(getClass().getResource(Const.francotiradorPerder));
        }
        else if ( jugador.getNombre().equals("zapador") && valor){ 
            return new ImageIcon(getClass().getResource(Const.zapadorGanar));
        }
        else if ( jugador.getNombre().equals("zapador") && !valor){ 
            return new ImageIcon(getClass().getResource(Const.zapadorPerder));
        }
        else{
            
    return new ImageIcon(getClass().getResource(Const.casillaVacia));
        }
    }

    
    
    
}
