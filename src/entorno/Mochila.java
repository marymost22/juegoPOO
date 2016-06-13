package entorno;
import consola.ConsolaNormal;
import item.Item;
import java.util.ArrayList;
import java.util.HashSet;


public class Mochila{
    private String nombre;
    private HashSet<Item> contenido; 
    private double pesoMax;
    private double pesoActual;
    private int numeroElementosMax;	
    private int numeroElementosActual;
    private String descripcion;
	
    public Mochila(String nombre, double peso, int numeroElementos, String descripcionCadena){
        this.nombre = nombre;
        if(peso>0 && numeroElementos>0){
            pesoMax = peso;
            numeroElementosMax = numeroElementos;
            contenido=new HashSet<>();
            if (descripcionCadena==null){
                descripcion = "Sin descripción.";
            }
            else {
                descripcion = descripcionCadena;
            }
        }
        else{
            ConsolaNormal consola = new ConsolaNormal();
            consola.imprimirln("Mochila(): Parámetros no válidos");
            System.exit(0);
        }
    }
    
    public Mochila(Mochila moch){
        this(moch.nombre, moch.pesoMax, moch.numeroElementosMax, moch.descripcion);
    }
    
    public Mochila (){ //Constructor que asigna valores por defecto
        nombre = "Mochila";
        pesoMax=40;
        numeroElementosMax=20;
        contenido= new HashSet <>();
        descripcion = " ";
    }

    /*Los setters de nombre, descripcion, pesoMaximo y numeroElementosMaximo no son necesarios
    pues estos valores se establecen en el constructor.
    No se implementa un setter del HashMap<Item, Integer> contenido, ya que la mochila
    en un inicio está vacia, para modificar su contenido están addItem() y removeItem()*/ 
    
    public void setPesoActual(double pesoActual) {
        if(pesoActual<=pesoMax)
            this.pesoActual = pesoActual;
    }

    public void setNumeroElementosActual(int numeroElementosActual) {
        if(numeroElementosActual<=numeroElementosMax)
            this.numeroElementosActual = numeroElementosActual;
    }    

    
    public String getNombre() {
        return nombre;
    }
    
    public double getPesoMax() {
        return pesoMax;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public int getNumeroElementosMax() {
        return numeroElementosMax;
    }

    public int getNumeroElementosActual() {
        return numeroElementosActual;
    }
    
    public String getDescripcion(){
        return descripcion;
    }

    public HashSet<Item> getContenido() {
        return contenido;
    }    
    
    public ArrayList <Item> getContenidoArray(){
        ArrayList <Item> array = new ArrayList<>();
        
        for (Item o : contenido){
            array.add(o);
        }
        return array;
    }

    
    //Método que permite obtener una instancia de la clase objeto sabiendo
    //su nombre
    public Item getItemNombre(String nombre){        
        if(nombre == null){
            ConsolaNormal consola = new ConsolaNormal();
            consola.imprimirln("Mochila.getItemNombre(): Nombre nulo");
            System.exit(0);
        }
        for (Item o : contenido)
            if (o.getNombre().toLowerCase().equals(nombre))
                return o;
        
        return null;
    }
    
    public boolean addItem(Item objeto){ 
        if(objeto == null){
            ConsolaNormal consola = new ConsolaNormal();
            consola.imprimirln("Mochila.addItem(): Objeto nulo");
            System.exit(0);
        }
        
        if((objeto.getPeso()+pesoActual)<=pesoMax && numeroElementosActual+1<=numeroElementosMax){
            contenido.add(objeto);
            pesoActual += objeto.getPeso();
            numeroElementosActual ++;

            return true;
        }
        
        else return false;        
    }
    
    public boolean removeItem(Item objeto){//Saca un objeto de la mochila 
        if(contenido.contains(objeto)){ 
            contenido.remove(objeto);
            
            pesoActual -= objeto.getPeso();
            numeroElementosActual--;
            
            return true;
        }
        else return false;
    }
    
    
       
    @Override    
    public String toString(){
        StringBuilder contenidoMochila = new StringBuilder();
        boolean a;
        
        if(contenido==null || contenido.isEmpty()){
            contenidoMochila.append("La mochila esta vacia, mira en la celdas... puedes encontar cosas muy interesantes.");
        }
        else{
            for(Item obj : contenido){
                contenidoMochila.append(obj.getNombre()).append("\n");
            }
        }
        
        return contenidoMochila.toString();
    }   
    
    public boolean contains(Item item){
        return contenido.contains(item);
    }
}