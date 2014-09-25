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

import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name = "ficha", catalog = "monopoly_db")
public class Ficha implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TIPO_AUTO = "auto";
	public static final String TIPO_SOMBRERO = "sombrero";
	public static final String TIPO_BOTA = "bota";
	public static final String TIPO_PLANCHA = "plancha";
	public static final String TIPO_CARRETILLA = "carretilla";
	public static final String TIPO_DEDAL = "dedal";
	public static final String TIPO_BARCO = "barco";
	public static final String TIPO_PERRO = "perro";
	public static final String TIPO_BOLSA_DINERO = "bolsa de dinero";
	public static final String TIPO_CABALLO = "caballo";
	public static final String TIPO_CANON = "cañón";
	
	public enum TipoFicha {
		F_AUTO("auto"),
		F_SOMBRERO("sombrero"),
		F_BOTA("bota"), 
		F_PLANCHA("plancha"), 
		F_CARRETILLA("carretilla"), 
		F_DEDAL("dedal"),
		F_BARCO("barco"),
		F_PERRO("perro"),
		F_BOLSA_DINERO("bolsa de dinero"),
		F_CABALLO("caballo"),
		F_CANON("cañón");

		private final String nombreTipo;

		TipoFicha(String nombre) {
			this.nombreTipo = nombre;
		}
		
		public String getNombreTipo(){
			return this.nombreTipo;
		}
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "fichaID")
	private Integer idFicha;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "pathImageBig")
	private String nombreImagenSeleccion;

	@Column(name = "pathImageSmall")
	private String nombreImagenCasillero;

	public Ficha() {
		super();
		GestorLogs.registrarLog("Nueva ficha '" + this.getNombre() + "'");
	}

	public Ficha(TipoFicha tipoFicha) {
		this();
		this.nombre = tipoFicha.getNombreTipo();
		GestorLogs.registrarLog("Nueva ficha '" + this.getNombre() + "'");
	}

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
		GestorLogs.registrarDebug("Asignado nombre a ficha '" + this.getNombre() + "'");
	}

	/**
	 * @return the idFicha
	 */
	public Integer getIdFicha() {
		return idFicha;
	}

	/**
	 * @param idFicha
	 *            the idJugador to set
	 */
	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return the nombreImagenSeleccion
	 */
	public String getNombreImagenSeleccion() {
		return nombreImagenSeleccion;
	}

	/**
	 * @param nombreImagenSeleccion
	 *            the nombreImagenSeleccion to set
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
	 * @param nombreImagenCasillero
	 *            the nombreImagenCasillero to set
	 */
	public void setNombreImagenCasillero(String nombreImagenCasillero) {
		this.nombreImagenCasillero = nombreImagenCasillero;
	}

	/**
	 * Devuelve true si la ficha pasada por parametro es igual a la que ejecuta
	 * el metodo compara en funcion del nombre (string) de la ficha
	 * 
	 * @param c
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		Ficha f = (Ficha) o;
		return this.getNombre().equals(f.getNombre());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Ficha [id: " + this.getIdFicha()
				+ ", nombre: " + this.getNombre() 
				+ "] }");
		return sb.toString();
	}

}
