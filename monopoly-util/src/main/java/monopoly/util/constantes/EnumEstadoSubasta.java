/**
 * 
 */
package monopoly.util.constantes;

import java.io.Serializable;

/**
 * 
 * @author Bostico Alejandro
 * @author pablo
 *
 */
public enum EnumEstadoSubasta implements Serializable {

	CREADA("creada"), INICIADA("Iniciada"),
	JUGANDO("Jugando"), FINALIZADA("Finalizada");

	private final String estadoSubasta;

	private EnumEstadoSubasta(String nombre) {
		estadoSubasta = nombre;
	}

	/**
	 * @return the nombreEstadoJuego
	 */
	public String getNombreEstadoJuego() {
		return estadoSubasta;
	}

}
