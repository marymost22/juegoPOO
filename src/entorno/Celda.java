package entorno;
import consola.ConsolaNormal;
import java.util.HashSet;
import java.util.Random;
import item.*;
import personaje.Enemigo;

public class Celda{
    private int coordenadaX;
    private int coordenadaY;
    private boolean transitable;
    private boolean visible;
    private boolean contieneJugador;
    private boolean dinamitada;
    private HashSet<Enemigo> enemigos;
    private HashSet<Item> items;
        
    //Constructor que permite controlar qué items contendrá una celda
    public Celda(boolean transitable, HashSet<Item> conjuntoObjetos, int x, int y) {
        
        if(conjuntoObjetos == null){
            ConsolaNormal consola = new ConsolaNormal();
            consola.imprimirln("Celda(): Conjunto nulo");
            System.exit(0);
        }
                
        this.transitable = transitable;
        this.items = conjuntoObjetos;
        this.visible = false;
        this.contieneJugador = false;
        this.enemigos = new HashSet();
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.dinamitada = false;
    }
    
    public Celda(boolean transitable, int x, int y) {
        this.transitable = transitable;
        this.items = new HashSet();
        this.visible = false;
        this.contieneJugador = false;
        this.enemigos = new HashSet();
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.dinamitada = false;
        
        /*En la lectura de archivos se crean todas las celdas como no transitables,
        posteriormente se asigna el valor de transitables a las que corresponda*/
        
        if(transitable){
            Random rnd = new Random();
            int aleatorio = rnd.nextInt(10);
            
            if(aleatorio == 0){
                int cantidadItems = rnd.nextInt(4)+2;                
                for(int i = 0; i<cantidadItems; i++){
                    addRandomItem();
                }                    
            }
            else if(aleatorio >= 1 && aleatorio <= 4){
                addRandomItem();
            }
        }
    }
    
    
    public void setTransitable(boolean valor){
        this.transitable = valor;        
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
        
    }

    public void setContieneJugador(boolean valor) {
        this.contieneJugador = valor;
    }
     
    public void setObjetos(HashSet <Item>objetos) {
        if(objetos == null){
            ConsolaNormal consola = new ConsolaNormal();
            consola.imprimirln("Celda.setObjetos(): Conjunto nulo");
            System.exit(0);
        }
        this.items = objetos;
    }    

    public void setDinamitada(boolean dinamitada) {
        this.dinamitada = dinamitada;
    }
        
    public boolean isTransitable() {//Getter
        return transitable;
    }

    public boolean isDinamitada() {
        return dinamitada;
    }
    
    public boolean isVisible() {//getter
        return visible;
    }
   
    public boolean getContieneJugador() {
        return contieneJugador;
    }
    
    public boolean hasEnemigos() {
        return !enemigos.isEmpty();
    }
    
    public boolean hasItems() {
        return !items.isEmpty();
    }
    
    public HashSet <Item> getItems() {
        return items;
    }

    public HashSet<Enemigo> getEnemigos() {
        return enemigos;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }
    
    public final boolean addItem(Item objeto){
        if(objeto == null) return false;
        
        items.add(objeto);        
        return false;
    }
    
    public final void addRandomItem(){
        Random rnd = new Random();
        
        switch(rnd.nextInt(6)){
            case 0:
                addItem(new Arma());
                break;
            case 1:
                addItem(new Armadura());
                break;
            case 2:
                addItem(new Binoculares());
                break;
            case 3:
                addItem(new Botiquin());
                break;
            case 4:
                addItem(new ToritoRojo());
                break;
            case 5:
                addItem(new Explosivo());
        }
    }
    
    public boolean removeItem(Item item){
        if(item == null) {
            ConsolaNormal consola = new ConsolaNormal();
            consola.imprimirln("Celda.removeObjeto(): Objeto nulo");
            System.exit(0);
        }
        
        if(items.contains(item)){
            items.remove(item);
            return true;
        }
        return false;
    }    
    
    public void addEnemigo(Enemigo enemigo){
        if(enemigo == null){
            new ConsolaNormal().imprimirln("Celda.addEnemigo(): Enemigo nulo");
            System.exit(0);
        }
        
        enemigos.add(enemigo);
    }
    
    public void removeEnemigo(Enemigo enemigo){
        if(enemigo == null){
            new ConsolaNormal().imprimirln("Celda.removeEnemigo(): Enemigo nulo");
            System.exit(0);
        }
        
        if (enemigos.contains(enemigo)){
            enemigos.remove(enemigo);
        }
    }
    
    //Método que permite obtener una instancia de la clase Item sabiendo
    //su nombre
    public Item getObjetoNombre(String nombre){
        if(nombre == null){
            new ConsolaNormal().imprimirln("Celda.getObjetoNombre(): Nombre nulo");
            System.exit(0);
        }
        
        for (Item o:items){ 
            if(o.getNombre().toLowerCase().equals(nombre)) {
                return o;            
            }
        }
        
        return null;   
    }
    
    //Método que permite obtener una instancia de la clase Enemgio sabiendo
    //su nombre
    public Enemigo getEnemigoNombre(String nombre){
        if(nombre == null){
            new ConsolaNormal().imprimirln("Enemigo.getEnemigoNombre(): Nombre nulo");
            System.exit(0);
        }

        for (Enemigo e:enemigos){
            if(e.getNombre().toLowerCase().equals(nombre)) 
                return e;          
        }
        return null;
    }
    
    //Método que permite obtener la codificación adecuada según el contenido de la celda
    public char getSprite() {
        if(isVisible()){
            if(!isTransitable()){
                return '#';
            }
            else if(contieneJugador){
                return '@';
            }
            else if(dinamitada){
                return 'T';
            }
            else if(!enemigos.isEmpty()){
                return 'X';
            }
            else{
                return 'O';
            }
        }
        else{
            return '*';
        }
    } 
    
    @Override
    public String toString(){
        StringBuilder cadena = new StringBuilder();
        
        for(Item o:items){
            cadena.append(o.getNombre());
            cadena.append("\n");
        }
        
        return cadena.toString();
    }
    
    public boolean contains(Item item){
        return items.contains(item);
    }
}