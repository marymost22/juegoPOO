
package consola;

import entorno.Celda;
import item.Item;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.*;
import javax.swing.JPopupMenu;
import juego.Juego;

public class MouseListener extends MouseAdapter  {


    private Juego juego;
    private GUI gui;
    private Item item;
    private Celda celda;
    private String estado;
    private JPopupMenu popupDerecho;
    private JPopupMenu popupIzquierdo;

    public MouseListener(Juego juego, GUI gui,  Celda celda ){
        this.celda = celda;
        this.juego= juego;
        this.gui = gui;
        item = null;
        estado = null;

    }
    
    public MouseListener(Juego juego, GUI gui,  String estado ){
        this.celda = null;
        this.juego= juego;
        this.gui = gui;
        item = null;
        this.estado = estado;
        
    }
    public MouseListener (Juego juego, GUI gui, Item item){
        this.estado = null;
        this.juego= juego;
        this.celda = null;
        this.gui = gui;
        this.item=item;
    }

    @Override
     public void mouseReleased (MouseEvent evt) {

        switch (evt.getButton()){
            case (BUTTON1):
                 
                    setPopupIzquierdoLavel();
                    popupIzquierdo.show(evt.getComponent(),
                      evt.getX(), evt.getY());

                break;
            case(BUTTON3):
                 if (evt.isPopupTrigger()) {
                    setPopupDerechoLavel();
                    popupDerecho.show(evt.getComponent(),
                      evt.getX(), evt.getY());
                    
                }
                break;

        }
    }



    private void setPopupDerechoLavel (){
        popupDerecho= new JPopupMenu();

        if (celda!= null && celda.hasEnemigos() && celda.isVisible()){
            popupDerecho.add(new MenuEnemigosDerecho(juego, gui, celda));
        }
        if (celda!= null &&  celda.hasItems()  && celda.isVisible()){
            popupDerecho.add(new MenuItems(juego, gui, celda));
        }
        if (celda!= null && celda.getContieneJugador() && celda.isVisible()){
            popupDerecho.add(new MenuJugador(juego,gui));
        }

    }
    
     private void setPopupIzquierdoLavel (){
        popupIzquierdo= new JPopupMenu();

        if (celda!=null && celda.hasEnemigos() && celda.isVisible() ){
            popupIzquierdo.add(new MenuEnemigosIzquierdo(juego, gui, celda));
        }
        
        else if( celda == null && item!=null){
            popupIzquierdo.add(new MenuItems(juego, gui, item));
        }
        
        else if (estado!= null){
            popupIzquierdo.add(new MenuItems(juego, gui, estado));
        }

    }
    
}