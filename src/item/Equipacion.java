package item;

import entorno.Celda;
import entorno.Mapa;
import java.util.HashSet;
import java.util.Iterator;

public class Equipacion {
    private Arma mano1;
    private Arma mano2;
    private Armadura armadura;
    private Binoculares binoculares;
    private final boolean restringido;
    private double multEnergiaCons;
    
    public Equipacion(boolean rest){
        mano1 = null;
        mano2 = null;
        armadura = null;
        binoculares = null;
        restringido = rest;
        multEnergiaCons = 1.0;
    }
    
    public double getIndiceDano(Mapa mapa, Celda actual, Celda destino){
        if(mano1 == null && mano2 == null){
            return mapa.distanciaCeldas(actual, destino) <= 1? 1.0:0;
        }
        else if(mano1 != null && mano1.isDosManos() && restringido){
            return mapa.distanciaCeldas(actual, destino) <= mano1.getAlcance()? mano1.getMultDano():0;
        }
        else{
            double indice = 0;
            
            if(mano1 != null && mapa.distanciaCeldas(actual, destino) <= mano1.getAlcance()){
                indice += mano1.getMultDano();
            }
            if(mano2 != null && mapa.distanciaCeldas(actual, destino) <= mano2.getAlcance()){
                indice += mano2.getMultDano();
            }
            
            return indice;
        }
    }
    
    public HashSet<Arma> getArmas(){
        HashSet<Arma> armas = new HashSet();
        
        if(mano1 != null && mano1.isDosManos() && restringido){
            armas.add(mano1);
        }
        else{
            if(mano1 != null){
                armas.add(mano1);
            }
            if(mano2 != null){
                armas.add(mano2);
            }
        }
        
        return armas;
    }

    public Armadura getArmadura() {
        return armadura;
    }

    public Binoculares getBinoculares() {
        return binoculares;
    }
    
    public HashSet<Item> getItems(){
        HashSet<Item> items = new HashSet(getArmas());
        
        if(armadura != null){
            items.add(armadura);
        }
        
        if(binoculares != null){
            items.add(binoculares);
        }
        
        return items;
    }
    
    public double getIndiceArmadura(){
        if(armadura == null){
            return 1;
        }
        return armadura.getMultDano();
    }
    
    public int getRangoBinoculares(){
        if(binoculares == null){
            return 0;
        }
        return binoculares.getAumentoVision();
    }

    public Arma getMano1() {
        return mano1;
    }

    public Arma getMano2() {
        return mano2;
    }
    
    
    
    public String armasToString(Mapa mapa, Celda actual, Celda destino){
        if(mano1 == null && mano2 == null){
            return mapa.distanciaCeldas(actual, destino) <= 1? "tus puños":null;
        }
        else if(mano1 != null && mano1.isDosManos() && restringido){
            return mapa.distanciaCeldas(actual, destino) <= mano1.getAlcance()? "tu "+mano1.getNombre():null;
        }
        else{
            String res = null;
            
            if(mano1 != null && mapa.distanciaCeldas(actual, destino) <= mano1.getAlcance()){
                res = mano1.getNombre();
            }
            if(mano2 != null && mapa.distanciaCeldas(actual, destino) <= mano2.getAlcance()){
                if(res == null){
                    res = mano2.getNombre();
                }
                else{
                    res += " y "+ mano2.getNombre();
                }
            }
            
            return res;
        }
    }
    
    public double getMultEnergiaCons(){
        return multEnergiaCons;
    }
    
    public Equipable desequipar(Equipable item){
        Equipable e = null;
        
        if(item instanceof Arma){
            e = desequipar((Arma)item);
        }
        else if(item instanceof Armadura){
            e = desequipar((Armadura)item);
        }
        else if(item instanceof Binoculares){
            e = desequipar((Binoculares)item);
        }
        
        return e;
    }
    
    public boolean equipar(Equipable item){
        boolean res = false;
        
        if(item instanceof Arma){
            res = equipar((Arma)item);
        }
        else if(item instanceof Armadura){
            res = equipar((Armadura)item);
        }
        else if(item instanceof Binoculares){
            res = equipar((Binoculares)item);
        }
        
        return res;
    }
    
    private boolean equipar(Arma arma){
        boolean res;
        
        if(arma.isDosManos() && restringido){            
            if(mano1 == null && mano2 == null){
                mano1 = arma;
                mano2 = arma;
                res = true;
            }
            else{
                res = false;
            }
        }
        else{
            if(mano1 == null){
                mano1 = arma;
                res = true;
            }
            else if(mano2 == null){
                mano2 = arma;
                res = true;
            }
            else{
                res = false;
            }
        }
        
        if(!restringido && mano1 != null && mano1.isDosManos() && mano2 != null && mano2.isDosManos()){
            multEnergiaCons = 1.5;
        }
        
        return res;
    }
    
    private boolean equipar(Armadura a){
        if(armadura == null){
            armadura = a;
            return true;
        }
        
        return false;
    }
    
    private boolean equipar(Binoculares b){
        if(binoculares == null){
            binoculares = b;
            return true;
        }
        
        return false;
    }
    
    private Arma desequipar(Arma arma){
        Arma res = null;
        
        if(arma.equals(mano1)){
            res = mano1;
            mano1 = null;
        }
        if(arma.equals(mano2)){
            res = mano2;
            mano2 = null;
        }
        
        if(restringido || mano1 == null || !mano1.isDosManos() || mano2 == null || !mano2.isDosManos()){
            multEnergiaCons = 1.0;
        }
        
        return res;
    }
    
    private Armadura desequipar(Armadura a){
        Armadura res = null;
        
        if(a.equals(armadura)){
            res = armadura;
            armadura = null;
        }
        
        return res;
    }
    
    public HashSet<Equipable> getEquipables(){
        HashSet<Equipable> resultado = new HashSet();
        
        if(mano1 != null && mano1.isDosManos() && restringido){
            resultado.add(mano1);
        }
        else{
            if(mano1 != null){
                resultado.add(mano1);
            }
            if(mano2 != null){
                resultado.add(mano2);
            }
        }
        
        if(armadura != null){
            resultado.add(armadura);
        }
        if(binoculares != null){
            resultado.add(binoculares);
        }
        
        return resultado;
    }
    
    private Binoculares desequipar(Binoculares b){
        Binoculares res = null;
        
        if(b.equals(binoculares)){
            res = binoculares;
            binoculares = null;
        }
        
        return res;
    }
    
    public Arma getBetterArma(){
        Arma better, aux;
        
        if(!getArmas().isEmpty()){
            Iterator <Arma> it = getArmas().iterator();
            better = it.next();
            
            while(it.hasNext()){
                aux = it.next();
                if(better.getMultDano() < aux.getMultDano()){
                    better=aux;
                }
            }
            return better;
        }
        return null;
    }
    
    public Arma getWorstArma(){
        Arma worst, aux;
        
        if(!getArmas().isEmpty()){
            Iterator <Arma> it = getArmas().iterator();
            worst = it.next();
            
            while(it.hasNext()){
                aux = it.next();
                if(worst.getMultDano() > aux.getMultDano()){
                    worst=aux;
                }
            }
            return worst;
        }
        return null;
    }
    
    
    public boolean better(Arma arma){
        Arma aux = getBetterArma();
        return aux != null && arma.getMultDano() > aux.getMultDano();
    }
    
    
    public boolean better(Armadura armadura){
        return armadura.getMultDano()>getArmadura().getMultDano();
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        
        if(mano1 != null && mano1.isDosManos() && restringido){
            resultado.append("\t- Arma de dos manos: ").append(mano1.getNombre()).append("\n");
        }
        else{
            int mano = 1;
            
            if(mano1 != null){
                resultado.append("\t- Arma en mano 1: ").append(mano1.getNombre()).append("\n");
                mano++;
            }
            if(mano2 != null){
                resultado.append("\t- Arma en mano ").append(mano).append(": ").append(mano2.getNombre()).append("\n");
            }
        }
        if(armadura != null){
            resultado.append("\t- Armadura: ").append(armadura.getNombre()).append("\n");
        }
        if(binoculares != null){
            resultado.append("\t- Binoculares: ").append(binoculares.getNombre()).append("\n");
        }
        
        return resultado.toString();
    }
}