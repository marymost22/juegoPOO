/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola;


import comando.Comando;
import excepcion.*;
import javax.swing.JMenu;


public abstract class Menus extends JMenu{
        Comando cmd;
        Consola consola;
        
    public Menus(String string, Consola consola){
        super(string);
        cmd = null; 
        this.consola = consola;
    }
    
    
    public void run(){
        
        try{
            cmd.ejecutar();
        }
                catch (CeldaNoValida ex) {
                    consola.imprimir("Celda no válida");
                }
                catch(ComandoIncorrecto ex){
                    consola.imprimir("El comando no existe, o tiene un formato no válido");
                }
                catch(SinEnemigos ex){
                    consola.imprimir("No hay ningún enemigo aquí");
                }
                catch(EnemigoNoAqui ex){
                    consola.imprimir("El enemigo existe, pero no está aquí");
                }
                catch(EnemigoNoExiste ex){
                    consola.imprimir("El enemigo no existe");
                }
                catch(InsuficienteEnergia ex){
                    consola.imprimir("No tienes energia suficiente");
                }
                catch(ItemNoEquipable ex){
                    consola.imprimir("Ese ítem no es equipable");
                }
                catch(ItemNoExiste ex){
                    consola.imprimir("Ese ítem no existe");
                }
                catch(ItemNoEnMochila ex){
                    consola.imprimir("Ese ítem no está en tu mochila");
                }
                catch(ItemNoUsable ex){
                    consola.imprimir("Ese ítem no es usable");
                }
                catch(ComandoExcepcion ex){
                    consola.imprimir("Error al procesar el comando");
                }   
            }
    }

