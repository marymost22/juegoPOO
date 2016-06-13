package entorno;
import consola.ConsolaNormal;
import item.Explosivo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import juego.Acciones;
import juego.Const;

public class Mapa{
    private int alto;   //Números
    private int ancho;  //Letras
    private final ArrayList<ArrayList<Celda>> celdas;
    private final HashSet<Explosivo> explosivos;

    public Mapa() {  //Mapa con dimensiones por defecto
        this.alto = Const.tamMapaDefecto;
        this.ancho = Const.tamMapaDefecto;
        Random rnd = new Random();
        celdas = new ArrayList<>();
        explosivos = new HashSet();
        
        for (int i=0; i<alto; i++){
            celdas.add(i,new ArrayList<>());
            for (int j=0; j<ancho; j++){
                boolean generable = getCelda(j-1, i)!=null && getCelda(j-1, i).isTransitable();
                generable = generable && getCelda(j, i-1)!=null && getCelda(j, i-1).isTransitable();
                
                if((rnd.nextInt(10) == 0 || rnd.nextInt(10)==1) && generable)
                    celdas.get(i).add(new Celda(false, j, i));
                else
                    celdas.get(i).add(new Celda(true, j, i));
            }
        }
    }
    
    public Mapa(int ancho, int alto){ //Constructor que permite determinar las dimensiones del mapa
        this.alto = alto;
        this.ancho = ancho;
        
        celdas = new ArrayList();
        explosivos = new HashSet();
        
        for(int i = 0; i<alto; i++){
            celdas.add(new ArrayList());
            for(int j = 0; j<ancho; j++){
                celdas.get(i).add(j, new Celda(false, j, i));
            }
        }
    }

   /*No se implementa un setter del  ArrayList<ArrayList<Celda>> celdas pues,
    una vez que una celda es creada en el constructor, no debería modificarse.
    */
    public void setAlto(int alto) {
        if (alto>0 && alto<=26)
            this.alto = alto;        
    }
    
    public void setAncho(int ancho) {
        if (ancho>0 && ancho<=100)
            this.ancho = ancho;        
    }
   
    public int getAlto() {
        return alto;
    }
    
    public int getAncho() {
        return ancho;
    }

    public HashSet<Explosivo> getExplosivos() {
        return explosivos;
    }
    
    /*En lugar de implementar un getter del ArrayList<ArrayList<Celda>> celdas, 
    en su lugar se implementa un getter que devuelva una celda concreta pues resulta
    mas intuitivo y util.
    */
    
    public final Celda getCelda(int x, int y){ //Es final porque se usa en el constructor
        if (x<0 || y<0 || y>=alto || x>=ancho)
            return null;
        
        return celdas.get(y).get(x);
    }    
    
    public void imprimirMapa(Acciones acciones){
        ConsolaNormal consola = new ConsolaNormal();        
        
        if(acciones == null){
            consola.imprimirln("Mapa.imprimirMapa(): Acciones nulas");
            System.exit(0);
        }
        
        int comienzoLog = alto - acciones.getNumAcciones();        
        
        for(int i=0; i<50; i++) consola.imprimirln("");
        
        consola.imprimir("   ");
        for(int y = -1; y<alto; y++){
            if(y!=-1) consola.imprimir((char)('A'+y) + "  ");
            
            for(int x = 0; x<ancho; x++){
                if(y == -1) consola.imprimir(x+(x>9? " ":"  "));
                else consola.imprimir(celdas.get(y).get(x).getSprite()+"  ");
            }
            
            consola.imprimir("|\t");
            if(y >= comienzoLog){
                consola.imprimir(acciones.getAccion(y - comienzoLog));
            }
            
            consola.imprimirln("");
        }
    }
    
    //Método que indica si una celda ubicada en la coordenada XY es adyacente a otra celda
    public int[] celdaAdyacente(Celda celda, int x, int y){
        int[] res = {0, 0};
        
        if(celda == null){
            return null;
        }
        
        for(int i = -1; i <= 1; i += 2){
            if(celda.equals(getCelda(x + i, y))){
                res[0] = i;
                return res;
            }
            else if(celda.equals(getCelda(x, y + i))){
                res[1] = i;
                return res;
            }
        }
        
        return null;
    }
    
    //Método que calcula la distancia entre 2 celdas
    public int distanciaCeldas(Celda c1, Celda c2){
        int x1, x2, y1, y2;
        
        x1 = c1.getCoordenadaX();
        y1 = c1.getCoordenadaY();
        x2 = c2.getCoordenadaX();
        y2 = c2.getCoordenadaY();
        
        return (int)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
    
    //Método que calcula la distancia efectiva entre 2 celdas
    public int distanciaAndar(Celda c1, Celda c2){
        int res;
        
        res = Math.abs(c1.getCoordenadaX() - c2.getCoordenadaX());
        res += Math.abs(c1.getCoordenadaY() - c2.getCoordenadaY());
        
        return res;
    }
}