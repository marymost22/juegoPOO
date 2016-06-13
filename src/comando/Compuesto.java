package comando;
import entorno.Celda;
import entorno.Mapa;
import excepcion.ComandoExcepcion;
import excepcion.ComandoIncorrecto;
import item.Item;
import java.util.ArrayList;
import java.util.HashSet;
import juego.Juego;
import personaje.Enemigo;

public class Compuesto implements Comando{
    private final ArrayList<Comando> comandos;
    
    public Compuesto(Juego juego, String cmd){
        comandos = new ArrayList();
        String rep;
        
        String[] campos = cmd.toLowerCase().split(" ");
        
        if((rep = campos[campos.length - 1]).matches("^\\d+$")){
            StringBuilder comandoRep = new StringBuilder();
            
            for(int i = 0; i < campos.length - 1; i++){
                comandoRep.append(campos[i]).append(" ");
            }
            
            comandos.add(new Repetido(juego, comandoRep.toString(), Integer.parseInt(rep)));
        }
        else{
            parseComando(juego, campos);
        }
    }
    
    @Override
    public void ejecutar() throws ComandoExcepcion{
        for(Comando c:comandos){
            if(c == null){
                throw new ComandoIncorrecto();
            }
            c.ejecutar();
        }
    }
    
    public final void parseComando(Juego juego, String[] campos){
        switch(campos[0]){
            case "atacar":
                if(campos.length == 2){
                    comandos.add(new Atacar(juego, parseCelda(juego.getMapa(), campos[1])));
                }
                else if(campos.length == 3){
                    comandos.add(new Atacar(juego, parseCelda(juego.getMapa(), campos[1]),
                            parseEnem(juego.getEnemigos(), campos[2])));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "ayuda":
                if(campos.length == 1){
                    comandos.add(new Ayuda(juego));
                }
                else{
                    comandos.add(null);
                }
                break;          
            case "coger":
                if(campos.length >= 2){
                    HashSet<Item> items = juego.getJugador().getCeldaActual().getItems();

                    for(int i = 1; i < campos.length; i++){
                        comandos.add(new Coger(juego.getJugador(), parseItem(items, campos[i])));
                    }                        
                }
                else{
                    comandos.add(null);
                }
                break;
            case "desequipar":
                if(campos.length >= 2){
                    HashSet<Item> items = juego.getJugador().getEquipacion().getItems();

                    for(int i = 1; i < campos.length; i++){
                        comandos.add(new Desequipar(juego.getJugador(), parseItem(items, campos[i])));
                    }
                }
                else{
                    comandos.add(null);
                }
                break;
            case "equipar":
                if(campos.length == 2 || campos.length == 3){
                    if(campos.length == 3){
                        comandos.add(new Compuesto(juego, "desequipar " + campos[2]));
                    }

                    HashSet<Item> items = juego.getJugador().getMochila().getContenido();
                    comandos.add(new Equipar(juego.getJugador(), parseItem(items, campos[1])));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "inventario":
                if(campos.length == 1){
                    comandos.add(new Inventario(juego.getJugador()));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "mirar":
                if(campos.length == 1){
                    comandos.add(new Mirar(juego.getJugador()));
                }
                else if(campos.length == 2){
                    Celda celda = parseCelda(juego.getMapa(), campos[1]);

                    if(celda != null){
                        comandos.add(new Mirar(juego.getJugador(), celda));
                    }
                    else{
                        HashSet<Item> items = juego.getJugador().getCeldaActual().getItems();                            
                        comandos.add(new Mirar(juego.getJugador(), parseItem(items, campos[1])));                            
                    }
                }
                else if(campos.length == 3){
                    Celda celda = parseCelda(juego.getMapa(), campos[1]);
                    Enemigo enemigo = parseEnem(juego.getEnemigos(), campos[2]);

                    comandos.add(new Mirar(juego.getJugador(), celda, enemigo));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "mover":
                if(campos.length == 2){
                    comandos.add(new Mover(juego.getJugador(), parseCelda(juego.getMapa(), campos[1])));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "pasar":
                if(campos.length == 1){
                    comandos.add(new Pasar(juego, juego.getEnemigos()));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "tirar":
                if(campos.length >= 2){
                    HashSet<Item> items = juego.getJugador().getMochila().getContenido();
                    
                    for(int i = 1; i < campos.length; i++){
                        comandos.add(new Tirar(juego.getJugador(), parseItem(items, campos[i])));
                    }
                }
                else{
                    comandos.add(null);
                }
                break;
            case "salir":
                if(campos.length == 1){
                    comandos.add(new Salir(juego));
                }
                else{
                    comandos.add(null);
                }
                break;
            case "usar":
                if(campos.length == 2){
                    HashSet<Item> items = juego.getJugador().getMochila().getContenido();
                    
                    for(int i = 1; i < campos.length; i++){
                        comandos.add(new Usar(juego.getJugador(), parseItem(items, campos[1])));                        
                    }
                }
                else{
                    comandos.add(null);
                }
                break;
            default:
                comandos.add(null);
        }
    }
    
    public final Celda parseCelda(Mapa mapa, String coordenadas){
        int x, y;
        
        if(coordenadas.matches("[0-9][a-z]")){
            x = coordenadas.charAt(0) - '0';
            y = coordenadas.charAt(1) - 'a';
        }
        else if(coordenadas.matches("[0-9][0-9][a-z]")){
            x = 10*(coordenadas.charAt(0) - '0') + coordenadas.charAt(1) - '0';
            y = coordenadas.charAt(2) - 'a';
        }
        else if(coordenadas.matches("[a-z][0-9]")){
            x = coordenadas.charAt(1) - '0';
            y = coordenadas.charAt(0) - 'a';
        }
        else if(coordenadas.matches("[a-z][0-9][0-9]")){
            x = 10*(coordenadas.charAt(1) - '0') + coordenadas.charAt(2) - '0';
            y = coordenadas.charAt(0) - 'a';
        }
        else{
            return null;
        }
        
        return mapa.getCelda(x, y);
    }
    
    public final Enemigo parseEnem(HashSet<Enemigo> enemigos, String enemigo){
        for(Enemigo e:enemigos){
            if(e.getNombre().equals(enemigo)){
                return e;
            }
        }
        
        return null;
    }
    
    public final Item parseItem(HashSet<Item> items, String item){
        for(Item i:items){
            if(i.getNombre().equals(item)){
                return i;
            }
        }
        
        return null;
    }
}