
package consola;

import item.Equipacion;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import personaje.Jugador;


public class PanelEstado extends PanelGUI{
    

    private int altoImagen;
    private int anchoImagen;
    private int altoCasilla;
    private int anchoCasilla;
    private Jugador jugador;
    private JLabel imagenPersonaje;
    private JLabel salud;
    private JLabel toritoRojo;
    private JLabel armadura;
    private JLabel arma1;
    private JLabel arma2;
    private JLabel binoculares;

    
             
    public PanelEstado(GUI gui){
        super(gui);
        
        jugador = gui.getJuego().getJugador();
        
        altoImagen= gui.getAltoPanelInferior();
        anchoImagen = altoImagen;
        
        altoCasilla= gui.getAltoPanelInferior()-30;
        anchoCasilla= ((gui.getAnchoPanelInferior()-anchoImagen)/6)-10;
        
        salud = new JLabel();
        toritoRojo = new JLabel();
        salud  = new JLabel();
        armadura = new JLabel();
        arma1 = new JLabel();
        arma2 = new JLabel();
        binoculares = new JLabel();
        
        setSigno(salud, "salud");
        setSigno(toritoRojo, "toritoRojo");
        setSigno(armadura, "armadura");
        setSigno(arma1, "arma1");
        setSigno(arma2, "arma2");
        setSigno(binoculares, "binoculares");
        
        
        setBackground(Color.WHITE);
        setMinimumSize(new java.awt.Dimension(gui.getAnchoPanelInferior(), gui.getAltoPanelInferior()));
        setPreferredSize(new java.awt.Dimension(gui.getAnchoPanelInferior(), gui.getAltoPanelInferior()));
        
        this.setLayout(new FlowLayout());
        
        crearPanelImagen();
        
        updatePanelSignos();
        
    }
    
    private void setSigno(  JLabel signo, String estado){
        
        signo.setOpaque(true);
        signo.setVisible(true);
        signo.setBackground(Color.WHITE);

        ImageIcon icono = new ImageIcon(getImagen(estado).getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
        signo.setIcon(icono);
        if(!"toritoRojo".equals(estado)){
            signo.addMouseListener(new MouseListener(gui.getJuego(), gui, estado));
        }
        this.add(signo);
    }
    
    
    private void crearPanelImagen(){
         
        imagenPersonaje = new JLabel();
        
        imagenPersonaje.setBackground(Color.WHITE);
        imagenPersonaje.setPreferredSize(new java.awt.Dimension(anchoImagen, altoImagen));
        ImageIcon icono = new ImageIcon(getImagen(jugador).getImage().
                                    getScaledInstance(anchoImagen,altoImagen,Image.SCALE_SMOOTH));  
        imagenPersonaje.setIcon(icono);
        
        this.add(imagenPersonaje);
        
    }


    protected void updatePanelSignos(){
        Equipacion equipacion = jugador.getEquipacion();
        if (equipacion.getArmadura() != null){
            ImageIcon icono = new ImageIcon(getImagen("armadura").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            armadura.setIcon(icono);
            
        }
        else{
            ImageIcon icono = new ImageIcon(getImagen("NoArmadura").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            armadura.setIcon(icono); 
        }
        if (equipacion.getMano1()!= null){
            ImageIcon icono = new ImageIcon(getImagen("arma1").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            arma1.setIcon(icono); 

        }
        else{
            ImageIcon icono = new ImageIcon(getImagen("NoArma1").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            arma1.setIcon(icono); 

        }
        if (equipacion.getMano2() != null){
            ImageIcon icono = new ImageIcon(getImagen("arma2").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            arma2.setIcon(icono); 

        }
        else{
            ImageIcon icono = new ImageIcon(getImagen("NoArma2").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            arma2.setIcon(icono); 

        }
        if (jugador.isToreado()){
            ImageIcon icono = new ImageIcon(getImagen("toritoRojo").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            toritoRojo.setIcon(icono); 
            
        }
        else{
            ImageIcon icono = new ImageIcon(getImagen("NoToritoRojo").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
            toritoRojo.setIcon(icono); 
            
        }
        
        if (jugador.getBinoculares() || jugador.getEquipacion().getBinoculares()!=null){
            ImageIcon icono = new ImageIcon(getImagen("binoculares").getImage().
                    getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));
            binoculares.setIcon(icono); 
            
        }
        else{
            ImageIcon icono = new ImageIcon(getImagen("NoBinoculares").getImage().
                    getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));
            binoculares.setIcon(icono); 
            
        }
        
        ImageIcon icono = new ImageIcon(getImagen("salud").getImage().
                                getScaledInstance(anchoCasilla,altoCasilla,Image.SCALE_SMOOTH));  
        salud.setIcon(icono); 
  }
    
    protected void updatePersonaje(){
        ImageIcon icono = new ImageIcon(getImagen(jugador).getImage().
                                getScaledInstance(anchoImagen,altoImagen,Image.SCALE_SMOOTH));  
        imagenPersonaje.setIcon(icono);
    }
    
}
