/**
 * 
 */
package monopoly.model;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Ficha {
    
    private String nombre;


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     
    
    public boolean equals(Ficha f) {
        
        return this.getNombre().equals(f.getNombre());
    }


    @Override
    public String toString() {
	return "Ficha [nombre=" + nombre + "]";
    }
    
    


}
