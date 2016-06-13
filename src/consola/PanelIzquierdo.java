/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import item.Item;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import juego.Juego;


public class PanelIzquierdo extends PanelGUI{
    private JTextArea actividad;
    private JScrollPane scroll;
    private JPanel inventario;
    
    private int ancho;
    private int alto;
    private int altoInventario = 200;
    private int filasInventario;        
    private int columnasInventario = 5;
    
    private ArrayList<ArrayList<JLabel>> contenidoInventario;
    
    private int tamanoCasilla;
    private Juego juego;
    
    public PanelIzquierdo(GUI gui){
        
        super(gui);
        juego = gui.getJuego();
        alto = gui.getAltoPanelIzquieda();
        ancho = gui.getAnchoPanelIzquierda();
        
        setBackground(Color.WHITE);
        setMinimumSize(new java.awt.Dimension(ancho, alto));
        setPreferredSize(new java.awt.Dimension(ancho, alto));
        
        crearPanelInventario();
                
        crearMensajes();
        
        javax.swing.GroupLayout panelDetallesLayout = new javax.swing.GroupLayout(this);
        this.setLayout(panelDetallesLayout);
        panelDetallesLayout.setHorizontalGroup(
            panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetallesLayout.createSequentialGroup()
                .addComponent(inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        panelDetallesLayout.setVerticalGroup(
            panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetallesLayout.createSequentialGroup()
                .addComponent(inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

       
    }
    
    private void crearMensajes(){
        actividad = new JTextArea();
        scroll = new JScrollPane();
        scroll.setMaximumSize(new java.awt.Dimension(ancho, alto-altoInventario));
        scroll.setMinimumSize(new java.awt.Dimension(ancho, alto-altoInventario));
        scroll.setPreferredSize(new java.awt.Dimension(ancho, alto-altoInventario));

        actividad.setFont(new Font("arial", Font.PLAIN, 16));
        actividad.setLineWrap(true);
        actividad.setWrapStyleWord(true);
        actividad.setEditable(false);
        actividad.setFocusable(false);
        
        actividad.setBorder(null);
        scroll.setBorder(null);
        
        scroll.setViewportView(actividad);
        
        
    }
    
    private void crearPanelInventario(){
        
        inventario = new JPanel();
        inventario.setBorder(null);
        
        inventario.setMaximumSize(new java.awt.Dimension(ancho, altoInventario));
        inventario.setMinimumSize(new java.awt.Dimension(ancho, altoInventario));
        inventario.setPreferredSize(new java.awt.Dimension(ancho, altoInventario));

        tamanoCasilla =  (ancho/columnasInventario)-1;
        filasInventario = altoInventario/tamanoCasilla; 
        
        inventario.setLayout(new java.awt.GridLayout (filasInventario,columnasInventario));
       
        Iterator<Item> itr = juego.getJugador().getMochila().getContenido().iterator();
        Icon icono;
        
        contenidoInventario = new ArrayList<>();
        for (int i =0; i< columnasInventario; i++){
            contenidoInventario.add(i, new ArrayList<>());
            for (int j=0; j<filasInventario; j++){
                contenidoInventario.get(i).add(new JLabel());
                contenidoInventario.get(i).get(j).setOpaque(true);
                contenidoInventario.get(i).get(j).setBounds((i*tamanoCasilla), (j*tamanoCasilla), 
                                                    tamanoCasilla, tamanoCasilla);
                contenidoInventario.get(i).get(j).setVisible(true);
                contenidoInventario.get(i).get(j).setBackground(Color.white);
                if (itr.hasNext()){
                    Item item = itr.next();
                    icono = new ImageIcon(getImagen(item).getImage().
                                    getScaledInstance(tamanoCasilla,tamanoCasilla,Image.SCALE_SMOOTH));  
                    contenidoInventario.get(i).get(j).addMouseListener(new MouseListener(juego, gui, (Item) item));
                }
                
                else{
                    icono = new ImageIcon(getImagen((Item)null).getImage().
                                    getScaledInstance(tamanoCasilla,tamanoCasilla,Image.SCALE_SMOOTH));        
                    contenidoInventario.get(i).get(j).addMouseListener(new MouseListener(juego, gui,(Item) null));
                }
                contenidoInventario.get(i).get(j).setIcon(icono);
                
                inventario.add(contenidoInventario.get(i).get(j));
            }
        }
    }
    
    public JTextArea getActividad() {
        return actividad;
    }

    public JPanel getInventario() {
        return inventario;
    }
    
    public void updateActividad(String actividadCadena){
        Calendar calendario = Calendar.getInstance();
        
        this.actividad.append("[" + cadenaHora() +"] ["+juego.getJugador().getSalud()+","+juego.getJugador().getEnergia()+"]\n" +actividadCadena + "\n");
    }
    
    public void updateInventario(){
        Iterator<Item> itr = juego.getJugador().getMochila().getContenido().iterator();
        Icon icono;
        
        for (int i =0; i< columnasInventario; i++){
            for (int j=0; j<filasInventario; j++){
                if (itr.hasNext()){
                    Item item = itr.next();
                    icono = new ImageIcon(getImagen(item).getImage().
                                    getScaledInstance(tamanoCasilla,tamanoCasilla,Image.SCALE_SMOOTH));  
                    contenidoInventario.get(i).get(j).addMouseListener(new MouseListener(juego, gui, (Item) item));
                }
                else{
                    icono = new ImageIcon(getImagen((Item)null).getImage().
                                    getScaledInstance(tamanoCasilla,tamanoCasilla,Image.SCALE_SMOOTH));        
                    contenidoInventario.get(i).get(j).addMouseListener(new MouseListener(juego, gui, (Item) null));
                }
                contenidoInventario.get(i).get(j).setIcon(icono);
            }
        }
    }
      
}
