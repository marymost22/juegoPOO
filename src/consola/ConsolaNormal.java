package consola;

import java.util.Scanner;

public class ConsolaNormal implements Consola{    
    public void imprimirln(String mensaje){
        System.out.println(mensaje);
    }
    
    @Override
    public void imprimir(String mensaje){
        System.out.print(mensaje);
    }
    
    @Override
    public String leer(String descripcion){
        Scanner lector = new Scanner(System.in);
        System.out.print(descripcion);
        
        return lector.nextLine();
    }

    @Override
    public void cerrar() {
       System.exit(0);
    }
}