/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

/**
 *
 * @author ngarcia
 */
public class ExcepcionInventario extends Exception{

    public ExcepcionInventario() {
    }
    
    public ExcepcionInventario(String message) {
        super(message);
    }

    public ExcepcionInventario(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcepcionInventario(Throwable cause) {
        super(cause);
    }


}
