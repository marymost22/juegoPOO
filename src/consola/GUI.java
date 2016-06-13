
package consola;

import entorno.Mapa;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import juego.Juego;


public class GUI extends javax.swing.JFrame implements Consola{
    private  Juego juego;
    
    private int filas;
    private int columnas;
    private int tamanoCelda;

    
    private int altoPanelMapa;
    private int anchoPanelMapa;
    
    private int altoPanelInferior;
    private int anchoPanelInferior;
    
    private int altoPanelIzquieda;
    private int anchoPanelIzquierda;
    
    private PanelIzquierdo panelIzquierdo;
    private PanelEstado panelInferior;
    private PanelMapa panelMapa;
        
    private ArrayList<ArrayList<JLabel>> mGrafica;

    public GUI() {
    }
  
    
    public GUI(Juego juego) {
        
        this.juego = juego;
        this.setLayout(new BorderLayout( ));
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setSize(900, 600);
        setResizable(false);
               
        pack();
                
        
        obtenerValores();
        panelMapa = new PanelMapa(this);
        obtenerValores();
        panelIzquierdo = new PanelIzquierdo(this);
        panelInferior = new PanelEstado(this);
        
        getContentPane().add(panelIzquierdo, java.awt.BorderLayout.LINE_START);
        getContentPane().add(panelMapa, java.awt.BorderLayout.CENTER);
        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);
        
        panelMapa.addKeyListener( new MapaKeyListener(juego, this));
        
        this.setVisible(true);
        this.setResizable(false); 
        panelMapa.createGraphMatrix();

    }


    public Juego getJuego() {
        return juego;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getTamanoCelda() {
        return tamanoCelda;
    }

    public int getAltoPanelMapa() {
        return altoPanelMapa;
    }

    public int getAnchoPanelMapa() {
        return anchoPanelMapa;
    }

    public int getAltoPanelInferior() {
        return altoPanelInferior;
    }

    public int getAnchoPanelInferior() {
        return anchoPanelInferior;
    }

    public int getAltoPanelIzquieda() {
        return altoPanelIzquieda;
    }

    public int getAnchoPanelIzquierda() {
        return anchoPanelIzquierda;
    }

    public PanelIzquierdo getPanelIzquierdo() {
        return panelIzquierdo;
    }

    public PanelMapa getPanelMapa() {
        return panelMapa;
    }

    public PanelEstado getPanelEstado() {
        return panelInferior;
    }
    
    
    
    

    public ArrayList<ArrayList<JLabel>> getmGrafica() {
        return mGrafica;
    }
    
    private void obtenerValores(){
        Mapa mapa = juego.getMapa();
        
        filas = mapa.getAlto();
        columnas = mapa.getAncho();
        altoPanelMapa = 500;
        //El alto se utiliza al máximo, hay que ajustar el ancho
        tamanoCelda = altoPanelMapa/filas;
        anchoPanelMapa = columnas*tamanoCelda;
        
        altoPanelIzquieda = 500;
        anchoPanelIzquierda = this.getSize().width - anchoPanelMapa;
        
        altoPanelInferior = 150;
        anchoPanelInferior = this.getSize().width;
        
    }
    
    
    public void cerrar(){
        setVisible(false);
    }

    @Override
    public void imprimir(String mensaje) {
        panelIzquierdo.updateActividad(mensaje);
    }

    @Override
    public String leer(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
}

    
   
