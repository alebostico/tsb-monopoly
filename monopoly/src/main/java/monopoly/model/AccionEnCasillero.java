/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

import monopoly.util.StringUtils;

/**
 * Enumerador para determinar que es lo que se debe realizar cuando el jugador
 * cae en un determinado casillero.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 **/
public enum AccionEnCasillero implements Serializable {

	/**
	 * Cuando una propiedad es libre para ser comprada String[descripción]
	 */
	DISPONIBLE_PARA_VENDER("La propiedad está disponible para la venta.",
			null, 0),

	/**
	 * Cuando otro jugador compró la propiedad, entonces hay que pagar alquiler.
	 * String[descripción + monto + nombre jugador][monto a pagar]
	 */
	PAGAR_ALQUILER("Debe pagar %s de alquiler al jugador %s.", null, 0),

	/**
	 * Cuando el casillero al que avanzó corresponde a una tarjeta de la suerte.
	 * String[descripción][Id Tarjeta de la Suerte]
	 */
	TARJETA_SUERTE("Saca una tarjeta de la suerte.", null, 0),

	/**
	 * Cuando el casillero al que avanzó corresponde a una tarjeta de comunidad.
	 * String[descripción][Id Tarjeta de Comunidad]
	 */
	TARJETA_COMUNIDAD("Saca una tarjeta comunidad.", null, 0),

	/**
	 * Cuando el casillero corresponde a Ir a la Cárcel. String[descripción]
	 */
	IR_A_LA_CARCEL("Fue a la cárcel.", null, 0),

	/**
	 * Cuándo el casillero corresponde a un descanso (Salida, Parking, En la
	 * Cárcel de visita) String[descripción]
	 */
	DESCANSO("Casillero %s, debe descansar.", null, 0),
	
	/**
	 * Cuando el jugador virtual inicia una subasta.
	 */
	EN_SUBASTA("En Subasta, esperando por apuestas.", null, 0),

	/**
	 * Cuándo el casillero corresponde a Impuesto de lujo
	 * String[Descripción][Monto]
	 */
	IMPUESTO_DE_LUJO(String.format(
			"El banco te cobró %s por el impuesto de lujo.",
			StringUtils.formatearAMoneda(100)), null, 100),

	/**
	 * Cuándo el casillero corresponde a Impuesto sobre el capital.
	 * String[Descripción]
	 */
	IMPUESTO_SOBRE_CAPITAL(
			"El banco te cobra 200 € o el 10% del valor total de su capital",
			null, 0),

	/**
	 * Cuándo el casillero corresponde a una propiedad comprada anteriormente.
	 * String[descripción]
	 */
	MI_PROPIEDAD("Ya adquirió esta propiedad.", null, 0),

	/*
	 * Cuándo el casillero corresponde a propiedad de otros jugador que se
	 * encuentra hipotecada. String[descripción]
	 */
	HIPOTECADA("La Propiedad %s del jugador %s se encuentra hipotecada.",
			"La propiedad %s se encuentra hipotecada", 0);

	private String mensaje;
	private String descripcion;
	private int monto;

	private AccionEnCasillero(String mensaje, String descripcion, int monto) {
		this.mensaje = mensaje;
		this.descripcion = descripcion;
		this.monto = monto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}
}
