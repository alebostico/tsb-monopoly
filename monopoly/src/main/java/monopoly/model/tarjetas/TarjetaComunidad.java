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

import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
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

		if (GestorLogs.getLoggingDetailLevel() == GestorLogs.MSG_DEBUG) {
			GestorLogs.registrarDebug("Tarjeta Comunidad " + this.idTarjeta
					+ " cargada");
		} else if (GestorLogs.getLoggingDetailLevel() == GestorLogs.MSG_DEBUG_DETAIL) {
			GestorLogs.registrarDebugDetail(this.toString());
		}

	}

	/**
     * 
     */
	public TarjetaComunidad() {
		super();
		// TODO Auto-generated constructor stub
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

}
