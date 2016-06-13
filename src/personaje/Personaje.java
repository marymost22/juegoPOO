package personaje;
import consola.Consola;
import entorno.*;
import item.*;
import consola.ConsolaNormal;

import excepcion.InsuficienteEnergia;
import excepcion.ItemNoUsable;
import java.util.Objects;


public abstract class Personaje {
    protected Consola consola;
    private String nombre;
    private int salud;
    private int saludBase;
    private int energia;
    private int energiaBase;
    private boolean toreado;
    private Mochila mochila;
    private Celda celdaActual;
    private int rangoVisionBase;
    private Equipacion equipacion;
    private Mapa mapa;
    private boolean binoculares;

    public abstract void mover(Celda destino) throws InsuficienteEnergia;
    public abstract void atacar(Personaje victima) throws InsuficienteEnergia;

    public Personaje(String nombre, int x, int y, int salud, int energia, Mapa mapa, boolean restriccion, Consola consola) {
        if(consola == null){
            System.out.println("Consola nula");
        }
        this.consola = consola;
        if (mapa == null || nombre == null) {
            consola.imprimir("Personaje() : Parametro nulo");
            System.exit(0);
        }

        this.nombre = nombre;
        this.saludBase = 100;
        this.salud = salud;
        this.energiaBase = 100;
        this.energia = energia;
        this.toreado = false;
        this.rangoVisionBase = 3;
        this.mapa = mapa;
        this.celdaActual = mapa.getCelda(x, y);
        this.equipacion = new Equipacion(restriccion);
        this.mochila = new Mochila();
    }

    public final void setNombre(String nombre) {
        if (nombre == null) {
            consola.imprimir("Jugador.setNombre(): Nombre nulo");
            System.exit(0);
        }
        this.nombre = nombre;
    }

    public void setSalud(int salud) {
        if (salud <= 0) {
            this.salud = 0;
        }else if(salud>this.saludBase){
            this.salud=saludBase;
        }
        else{
            this.salud = salud;
        }
    }

    public void setEnergia(int energia) {
        if (energia <= 0) {
            this.energia = 0;
        }
        else if (energia > energiaBase){
            this.energia = energiaBase;
        }
        else{
            this.energia = energia;
        }
    }

    public void setSaludBase(int saludBase) {
        this.saludBase = saludBase;
        
        if(this.salud > this.saludBase){
            this.salud = this.saludBase;
        }
    }

    public void setEnergiaBase(int energiaBase) {
        this.energiaBase = energiaBase;
        
        if(this.energia > this.energiaBase){
            this.energia = this.energiaBase;
        }
    }

    public void setToreado(boolean toreado) {
        this.toreado = toreado;
    }

    public void setMochila(Mochila mochila) {
        if (mochila == null) {

            consola.imprimir("Jugador.setMochila(): Mochila nula");
            System.exit(0);
        }

        this.mochila = new Mochila(mochila);
    }

    public void setCeldaActual(Celda celdaActual) {
        
        if(celdaActual == null){
            consola.imprimir("Personaje.setCeldaActual(): Celda nula");
            System.exit(0);
        }
        else if(!celdaActual.isTransitable()){
            consola.imprimir("Personaje.setCeldaActual(): Celda no transitable");
        }
        else{
            this.celdaActual = celdaActual;
        }
    }
    
    public void setBinoculares(boolean valor){
        binoculares = valor;
    }
    
    public void setConsola(Consola consola){
        if(consola!=null)
            this.consola = consola;
        
    }
  

    /*Los getters de juego, mochila y mapa no se implementan pues estos atributos
     tienen la función de ser unicamente una referencia para la clase Jugador 
     (podriamos decir que a modo de "propiedad"), ninguna otra clase debería 
     tener acceso a esta información.
     
     Los getters de los binoculares, armadura, objetoMano1 y objetoMano2 no tienen
     utilidad*/
    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaBase() {
        return energiaBase;
    }

    public Celda getCeldaActual() {
        return this.celdaActual;
    }

    public int getRangoVisionBase() {
        return rangoVisionBase;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Equipacion getEquipacion() {
        return equipacion;
    }

    public boolean isToreado() {
        return toreado;
    }
    
    public Mochila getMochila() {
        return mochila;
    }
    
    public boolean getBinoculares(){
        return binoculares;
    }
    
    public boolean coger(Item item) {
        if(item == null){
            consola.imprimir("Personaje.coger(): Item nulo");
            System.exit(0);
        }
        
        if (!(item instanceof Explosivo) && celdaActual.contains(item)){
            celdaActual.removeItem(item);
            mochila.addItem(item);
            return true;
        }
        return false;
    }
    
    public boolean tirar(Item item) {
        if(item == null){
            consola.imprimir("Personaje.tirar(): Item nulo");
            System.exit(0);
        }
        
        if(mochila.removeItem(item)){
            return celdaActual.addItem(item);
        }
        return false;
    }

    public boolean equipar(Item item) {
        if(item == null){
            consola.imprimir("Personaje.equipar(): Item nulo");
            System.exit(0);
        }
                
        if(item instanceof Equipable){
            if(!this.getEquipacion().equipar((Equipable)item)){
                return false;
            }
            
            mochila.removeItem(item);
            
            if(item instanceof Armadura){
                saludBase += this.getEquipacion().getArmadura().getAumentoSalud();
                energiaBase += this.getEquipacion().getArmadura().getAumentoEnergia();
            }
        }
     
        return true;
    }

    public boolean desequipar(Item item) {
        if(item == null){
            consola.imprimir("Personaje.desequipar(): Item nulo");
            System.exit(0);
        }
        
        
        if(item instanceof Equipable && item instanceof Armadura){
            int aumentoSalud = this.getEquipacion().getArmadura().getAumentoSalud();
            int aumentoEnergia = this.getEquipacion().getArmadura().getAumentoEnergia();
            
            if(this.getEquipacion().desequipar((Equipable)item) == null){
                return false;
            }
            this.getMochila().addItem(item);
            if(item instanceof Armadura){
                setSaludBase(saludBase - aumentoSalud);
                setEnergiaBase(energiaBase - aumentoEnergia);
            }
        }
        
        if(item instanceof Equipable){
            if(this.getEquipacion().desequipar((Equipable)item) == null){
                return false;
            }
            this.getMochila().addItem(item);
        } 
        
        return true;
    }
    
    public void usar(Item item) throws ItemNoUsable{
        if(item == null){
            consola.imprimir("Personaje.usar(): Item nulo");
            System.exit(0);
        }
        
        if(item instanceof Usable){
            item.usar(this);
            mochila.removeItem(item);
        }
    }

    public String getDetalles() {
        StringBuilder cadena = new StringBuilder();
        cadena.append(this.getNombre()).append("\n" + "Salud: ").append(this.getSalud()).
                append("\n" + "Energía: ").append(this.getEnergia()).append("\n");
        
        if(!this.equipacion.getEquipables().isEmpty()){
            cadena.append("Equipación: \n").append(this.equipacion.toString());
        }

        return cadena.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Personaje other = (Personaje) obj;
        return Objects.equals(this.nombre, other.nombre);
    }
}