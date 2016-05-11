/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

import monopoly.util.StringUtils;

/**
 * Clase para determinar que es lo que se debe realizar cuando el jugador cae en
 * un determinado casillero.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 **/
public class AccionEnCasillero implements Serializable {

	private static final long serialVersionUID = 2006479463146765881L;

	public enum Accion implements Serializable {

		/**
		 * Cuando una propiedad es libre para ser comprada String[descripción]
		 */
		DISPONIBLE_PARA_VENDER("La propiedad está disponible para la venta.",
				null),

		/**
		 * Cuando otro jugador compró la propiedad, entonces hay que pagar
		 * alquiler. String[descripción + monto + nombre jugador][monto a pagar]
		 */
		PAGAR_ALQUILER("Debe pagar %s de alquiler al jugador %s.", null),

		/**
		 * Cuando el casillero al que avanzó corresponde a una tarjeta de la
		 * suerte. String[descripción][Id Tarjeta de la Suerte]
		 */
		TARJETA_SUERTE("Saca una tarjeta de la suerte.", null),

		/**
		 * Cuando el casillero al que avanzó corresponde a una tarjeta de
		 * comunidad. String[descripción][Id Tarjeta de Comunidad]
		 */
		TARJETA_COMUNIDAD("Saca una tarjeta comunidad.", null),

		/**
		 * Cuando el casillero corresponde a Ir a la Cárcel. String[descripción]
		 */
		IR_A_LA_CARCEL("Fue a la cárcel.", null),

		/**
		 * Cuándo el casillero corresponde a un descanso (Salida, Parking, En la
		 * Cárcel de visita) String[descripción]
		 */
		DESCANSO("Casillero %s, debe descansar.", null),

		/**
		 * Cuando el jugador virtual inicia una subasta.
		 */
		EN_SUBASTA("En Subasta, esperando por apuestas.", null),

		/**
		 * Cuándo el casillero corresponde a Impuesto de lujo
		 * String[Descripción][Monto]
		 */
		IMPUESTO_DE_LUJO("El banco te cobró 100 € por el impuesto de lujo.",
				null),

		/**
		 * Cuándo el casillero corresponde a Impuesto sobre el capital.
		 * String[Descripción]
		 */
		IMPUESTO_SOBRE_CAPITAL(
				"El banco te cobra 200 € o el 10% del valor total de su capital",
				null),

		/**
		 * Cuándo el casillero corresponde a una propiedad comprada
		 * anteriormente. String[descripción]
		 */
		MI_PROPIEDAD("Ya adquirió esta propiedad.", null),

		/*
		 * Cuándo el casillero corresponde a propienumedad de otros jugador que
		 * se encuentra hipotecada. String[descripción]
		 */
		HIPOTECADA("La Propiedad %s del jugador %s se encuentra hipotecada.",
				"La propiedad %s se encuentra hipotecada");

		private String mensaje;
		private String descripcion;

		private Accion(String mensaje, String descripcion) {
			this.mensaje = mensaje;
			this.descripcion = descripcion;
		}

		/**
		 * @return El mensaje de la acción
		 */
		public String getMensaje() {
			return mensaje;
		}

		/**
		 * @return Más detalles sobre la acción
		 */
		public String getDescripcion() {
			return descripcion;
		}

	}

	private Accion accion;
	private String mensaje;
	private String descripcion;
	private int monto;

	/**
	 * Constructor de la clase {@code AccionEnCasillero}.
	 * 
	 * @param accion
	 *            un valor de Enum {@code Accion} de la clase.
	 */
	public AccionEnCasillero(Accion accion) {
		this.accion = accion;
		this.mensaje = accion.getMensaje();
		this.descripcion = accion.getDescripcion();
	}

	/**
	 * Constructor de la clase {@code AccionEnCasillero}.
	 * 
	 * @param accion
	 *            un valor de Enum {@code Accion} de la clase.
	 * @param monto
	 *            El monto de la transacción
	 */
	public AccionEnCasillero(Accion accion, int monto) {
		this(accion);
		this.monto = monto;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the monto
	 */
	public int getMonto() {
		return monto;
	}

	/**
	 * @param monto
	 *            the monto to set
	 */
	public void setMonto(int monto) {
		this.monto = monto;
	}

	/**
	 * @return the accion
	 */
	public Accion getAccion() {
		return accion;
	}

	public String toString() {
		return getAccion().toString();
	}
}
