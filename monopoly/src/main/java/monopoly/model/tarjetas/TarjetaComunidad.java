/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */

@Entity
@Table(name = "tarjeta_comunidad", catalog = "monopoly_db")
public class TarjetaComunidad extends Tarjeta implements Serializable {

	private static final long serialVersionUID = -8002053704891216523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tarjetaComunidadID")
	private int idTarjeta;

	@Column(name = "objetivo")
	private String objetivo;

	/**
	 * @param idTarjeta
	 * @param objetivo
	 */
	public TarjetaComunidad(int idTarjeta, String objetivo) {
		super();
		this.idTarjeta = idTarjeta;
		this.objetivo = objetivo;
	}

	/**
     * 
     */
	public TarjetaComunidad() {
		super();
	}

	/**
	 * @return the idTarjeta
	 */
	public int getIdTarjeta() {
		return idTarjeta;
	}

	/**
	 * @param idTarjeta
	 *            the idTarjeta to set
	 */
	public void setIdTarjeta(int idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	/**
	 * @return the objetivo
	 */
	public String getObjetivo() {
		return objetivo;
	}

	/**
	 * @param objetivo
	 *            the objetivo to set
	 */
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TarjetaComunidad [idTarjeta=" + idTarjeta + ", objetivo="
				+ objetivo + "]";
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		TarjetaComunidad casillero = (TarjetaComunidad) object;
		if (this.getIdTarjeta() != casillero.getIdTarjeta())
			return false;

		return true;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getIdTarjeta();
	}

}
