/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import comando.Coger;
import comando.Desequipar;
import comando.Equipar;
import comando.Mirar;
import comando.Pasar;
import comando.Tirar;
import comando.Usar;
import entorno.Celda;
import item.Arma;
import item.Armadura;
import item.Binoculares;
import item.Equipable;
import item.Item;
import item.Usable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import juego.Juego;

public class MenuItems extends Menus{
     
    public MenuItems(Juego juego, GUI gui, Celda celda){
        super ("Items", gui);
        
        if(celda==null){
            System.out.println("Error: Menú items");
        }
        
        for (Item i : celda.getItems()){
            JMenu menu = new JMenu(i.getNombre());
            
            JMenuItem coger =  new JMenuItem("Coger");
            coger.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {

                    cmd = new Coger(juego.getJugador(), i);
                    if (cmd!=null){
                        run();
                        gui.getPanelIzquierdo().updateInventario();
                    }
                }

            });
            
            JMenuItem detalles = new JMenuItem("Detalles");
            detalles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {

                cmd = new Mirar(juego.getJugador(), i);
                if (cmd!=null) run();
                }

            });
            
            menu.add(detalles);
            menu.add(coger);
            
            this.add(menu);
        }
    }
    
    public MenuItems (Juego juego, GUI gui, Item item){
        
        super(item.getNombre(), gui);
        JMenuItem itemOption = null;
        
        if(item instanceof Equipable){
            itemOption = new JMenuItem("Equipar");

            itemOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    cmd = new Equipar(juego.getJugador(), item);
                    if (cmd!=null){
                        run();
                        if(item instanceof Binoculares){
                            gui.getPanelMapa().updateGraphMatrix();
                        }
                        gui.getPanelEstado().updatePanelSignos();
                        gui.getPanelIzquierdo().updateInventario();
                        
                    }
                }
            });
        }
        
        else if( item instanceof Usable){
            itemOption = new JMenuItem("Usar");
            itemOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    cmd = new Usar(juego.getJugador(), item);
                    if (cmd!=null){
                        run();
                        if(item instanceof Binoculares){
                            gui.getPanelMapa().updateGraphMatrix();
                        }
                        gui.getPanelEstado().updatePanelSignos();
                        gui.getPanelIzquierdo().updateInventario();
                        
                    }
                }
            });
        }
        this.add(itemOption);
        
        itemOption = new JMenuItem("Soltar");

        itemOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cmd = new Tirar(juego.getJugador(), item);
                if (cmd!=null){
                    run();
                    gui.getPanelEstado().updatePanelSignos();
                    gui.getPanelIzquierdo().updateInventario(); 
                }
            }
        });
        this.add(itemOption);
            
    }
    
    public MenuItems (Juego juego, GUI gui, String estado){
        
        super(estado, gui);
        JMenuItem itemOption = null;

            if (estado.equals("armadura")){
                Armadura armadura = juego.getJugador().getEquipacion().getArmadura();
                if(armadura!=null){
                    itemOption = new JMenuItem("Desequipar");
                    itemOption.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                        cmd = new Desequipar(juego.getJugador(), armadura);
                        if (cmd!=null){
                            run();
                            gui.getPanelEstado().updatePanelSignos();
                            gui.getPanelIzquierdo().updateInventario();
                        }
                        }
                    });
                }
            }

            else if (estado.equals("arma1")){
                Arma arma= juego.getJugador().getEquipacion().getMano1();
                if(arma!=null){
                    itemOption = new JMenuItem("Desequipar");
                    itemOption.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                        cmd = new Desequipar(juego.getJugador(), arma);
                        if (cmd!=null){
                            run();
                            gui.getPanelEstado().updatePanelSignos();
                            gui.getPanelIzquierdo().updateInventario();
                        }
                        }
                    });
                }
            }
            
             else if (estado.equals("arma2")){
                Arma arma= juego.getJugador().getEquipacion().getMano2();
                if(arma!=null){
                    itemOption = new JMenuItem("Desequipar");
                    itemOption.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                        cmd = new Desequipar(juego.getJugador(), arma);
                        if (cmd!=null){
                            run();
                            gui.getPanelEstado().updatePanelSignos();
                            gui.getPanelIzquierdo().updateInventario();
                        }
                        }
                    });
                }
            }
            
            else if (estado.equals("binoculares")){
                Binoculares binoculares= juego.getJugador().getEquipacion().getBinoculares();
                if(binoculares!=null){
                    itemOption = new JMenuItem("Desequipar");
                    itemOption.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                        cmd = new Desequipar(juego.getJugador(), binoculares);
                        if (cmd!=null){
                            run();
                            gui.getPanelEstado().updatePanelSignos();
                            gui.getPanelIzquierdo().updateInventario();
                        }
                        }
                    });
                }
            }
            else if (estado.equals("salud")){
                itemOption = new JMenuItem("Pasar");
                itemOption.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        cmd = new Pasar(juego, juego.getEnemigos());
                        if (cmd!=null){
                            run();
                            gui.getPanelEstado().updatePersonaje();
                            gui.getPanelEstado().updatePanelSignos();
                            gui.getPanelIzquierdo().updateInventario();
                            gui.getPanelIzquierdo().updateActividad("Nuevo turno!");
                        }
                    }
                });
            }
            

    if(itemOption!=null) this.add(itemOption);
    }
}    