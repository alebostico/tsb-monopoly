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
import javax.persistence.Transient;

import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
@Entity
@Table(name = "ficha", catalog = "monopoly_db")
public class Ficha implements Serializable {

	private static final long serialVersionUID = -6170470808225734837L;
	
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

		public String getNombreTipo() {
			return this.nombreTipo;
		}
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "fichaID")
	private int idFicha;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "pathImageBig")
	private String pathImgBig;

	@Column(name = "pathImageSmall")
	private String pathImgSmall;

	@Transient
	private TipoFicha tipoFicha;

	@Transient
	private boolean isSelected = false;

	public Ficha() {
		super();
		GestorLogs.registrarLog("Nueva ficha '" + this.getNombre() + "'");
		isSelected = false;
	}

	public Ficha(TipoFicha tipoFicha) {
		this.nombre = tipoFicha.getNombreTipo();
		this.tipoFicha = tipoFicha;
		isSelected = false;
		GestorLogs.registrarLog("Nueva ficha '" + this.getNombre() + "'");
	}

	public Ficha(int id, TipoFicha tipoFicha, String pathImgSmall,
			String pathImgBig) {
		this.idFicha = id;
		this.nombre = tipoFicha.getNombreTipo();
		this.pathImgSmall = pathImgSmall;
		this.pathImgBig = pathImgBig;
		isSelected = false;
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
		GestorLogs.registrarDebug("Asignado nombre a ficha '"
				+ this.getNombre() + "'");
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
	 * @return the pathImgBig
	 */
	public String getPathImgBig() {
		return pathImgBig;
	}

	/**
	 * @param pathImgBig
	 *            the pathImgBig to set
	 */
	public void setPathImgBig(String pathImgBig) {
		this.pathImgBig = pathImgBig;
	}

	/**
	 * @return the pathImgSmall
	 */
	public String getPathImgSmall() {
		return pathImgSmall;
	}

	/**
	 * @param pathImgSmall
	 *            the pathImgSmall to set
	 */
	public void setPathImgSmall(String pathImgSmall) {
		this.pathImgSmall = pathImgSmall;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected
	 *            the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * Devuelve true si la ficha pasada por parametro es igual a la que ejecuta
	 * el metodo compara en funcion del nombre (string) de la ficha
	 * 
	 * @param o
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
		sb.append("{ Ficha [id: " + this.getIdFicha() + ", nombre: "
				+ this.getNombre() + "] }");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNombre().hashCode();
	}
	
	

}
