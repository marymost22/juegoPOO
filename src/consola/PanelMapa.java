/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;

import entorno.Mapa;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import juego.Juego;

public class PanelMapa extends PanelGUI {
    private Juego juego;
    private int tamanoCelda;
    private int filas;
    private int columnas;
    private ArrayList<ArrayList<JLabel>> mGrafica;
    private GUI gui;

    
    public PanelMapa(GUI gui ){
        super(gui);
        
        juego = gui.getJuego();
        tamanoCelda = gui.getTamanoCelda();
        filas = gui.getFilas();
        columnas = gui.getColumnas();
        mGrafica =  gui.getmGrafica();
        this.gui = gui;
        
        int alto = gui.getAltoPanelMapa();
        int ancho = gui.getAnchoPanelMapa();
        
        setMaximumSize(new java.awt.Dimension(600, 500));
        setMinimumSize(new java.awt.Dimension(600, 100));
        setPreferredSize(new java.awt.Dimension(ancho, alto));
        setBackground(Color.WHITE);
        setFocusable(true);
        
        javax.swing.GroupLayout panelMapaLayout = new javax.swing.GroupLayout(this);
        setLayout(panelMapaLayout);
        
        panelMapaLayout.setHorizontalGroup(
            panelMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, ancho, Short.MAX_VALUE)
        );
        panelMapaLayout.setVerticalGroup(
            panelMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, alto, Short.MAX_VALUE)
        );
}
    
    public void createGraphMatrix(){
        int i, j;
        Mapa mapa = juego.getMapa();

        this.setLayout(new GridLayout(filas, columnas, 0, 0));
        
        mGrafica = new ArrayList<>();
        for (i =0; i< filas; i++){
            mGrafica.add(i, new ArrayList<>());
            for (j=0; j<columnas; j++){
                mGrafica.get(i).add(new JLabel());
                mGrafica.get(i).get(j).setOpaque(true);
                mGrafica.get(i).get(j).setBounds((i*tamanoCelda)+tamanoCelda, (j*tamanoCelda)+tamanoCelda, 
                                                    tamanoCelda, tamanoCelda);
                mGrafica.get(i).get(j).setVisible(true);
                mGrafica.get(i).get(j).setBackground(Color.black);
                
            Icon icono = new ImageIcon(getImagen(mapa, i, j).getImage().
                                getScaledInstance(tamanoCelda,tamanoCelda,Image.SCALE_SMOOTH));
                
                mGrafica.get(i).get(j).setIcon(icono);
                mGrafica.get(i).get(j).addMouseListener(new MouseListener(juego,gui, mapa.getCelda(j, i))); 
                this.add(mGrafica.get(i).get(j));
            }
        }
        
        this.revalidate();
    }
     
    public void updateGraphMatrix(){
        Mapa mapa = juego.getMapa();
        
        for (int i =0; i< filas; i++){
            for (int j=0; j<columnas; j++){           
                    Icon icono = new ImageIcon(getImagen(mapa, i, j).getImage().
                                getScaledInstance(tamanoCelda,tamanoCelda,Image.SCALE_FAST));        
                    mGrafica.get(i).get(j).setIcon(icono);
            }
        }
    }
 
    
    public ArrayList<ArrayList<JLabel>> getmGrafica() {
        return mGrafica;
    }


}

   