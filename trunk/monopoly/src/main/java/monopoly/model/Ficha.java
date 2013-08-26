/**
 * 
 */
package monopoly.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Entity
@Table(name="ficha", catalog = "monopoly_db")
public class Ficha implements Serializable{
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "fichaID")
    private Integer idJugador;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "pathImageBig")
    private String nombreImagenSeleccion;
	
	@Column(name = "pathImageSmall")
    private String nombreImagenCasillero;

	/**
	 * 
	 * @return
	 */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
	 * @return the idJugador
	 */
	public Integer getIdJugador() {
		return idJugador;
	}


	/**
	 * @param idJugador the idJugador to set
	 */
	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}

	
	/**
	 * @return the nombreImagenSeleccion
	 */
	public String getNombreImagenSeleccion() {
		return nombreImagenSeleccion;
	}

	/**
	 * @param nombreImagenSeleccion the nombreImagenSeleccion to set
	 */
	public void setNombreImagenSeleccion(String nombreImagenSeleccion) {
		this.nombreImagenSeleccion = nombreImagenSeleccion;
	}

	/**
	 * @return the nombreImagenCasillero
	 */
	public String getNombreImagenCasillero() {
		return nombreImagenCasillero;
	}

	/**
	 * @param nombreImagenCasillero the nombreImagenCasillero to set
	 */
	public void setNombreImagenCasillero(String nombreImagenCasillero) {
		this.nombreImagenCasillero = nombreImagenCasillero;
	}

	/**
     * Devuelve true si la ficha pasada por parametro es igual a la que ejecuta el metodo
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
