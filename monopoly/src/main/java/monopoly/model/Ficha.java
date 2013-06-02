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
     
    /**
     * Devuelve true si la ficha pasadapor parametro es igual a la que ejecuta el metodo
     * compara en funcion del nombre (string) de la ficha
     * @param c
     * @return
     */
    @Override
    public boolean equals(Object o) {
	if ( o==null)
	    return false;
	if (o.getClass()!=this.getClass())
	return false;
	Ficha f=(Ficha)o;
        return this.getNombre().equals(f.getNombre());
    }


    @Override
    public String toString() {
	return "Ficha [nombre=" + nombre + "]";
    }
    
    


}
