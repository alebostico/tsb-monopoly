/**
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.List;

import monopoly.model.Estado.EstadoJuego;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.Tarjeta;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGameStatus implements Serializable {

	private static final long serialVersionUID = -584315517388176762L;

	/**
	 * Enumerador para determinar que es lo que se debe realizar cuando el
	 * jugador cae en un determinado casillero.
	 **/
	public enum AccionEnCasillero {
		/**
		 * Cuando una propiedad es libre para ser comprada String[descripción]
		 */
		DISPONIBLE_PARA_VENDER(new String[] { "Disponible para la Venta" }),

		/**
		 * Cuando otro jugador compró la propiedad, entonces hay que pagar
		 * alquiler. String[descripción + monto + nombre jugador][monto a pagar]
		 */
		PAGAR_ALQUILER(new String[] {
				"Debes pagar €{0} de alquiler al Jugador {0} ", "€" }),

		/**
		 * Cuando el casillero al que avanzó corresponde a una tarjeta de la
		 * suerte. String[descripción][Id Tarjeta de la Suerte]
		 */
		TARJETA_SUERTE(new String[] { "Tarjeta Suerte", "IdTarjeta" }),

		/**
		 * Cuando el casillero al que avanzó corresponde a una tarjeta de
		 * comunidad. String[descripción][Id Tarjeta de Comunidad]
		 */
		TARJETA_COMUNIDAD(new String[] { "Tarjeta Comunidad", "IdTarjeta" }),

		/**
		 * Cuando el casillero corresponde a Ir a la Cárcel. String[descripción]
		 */
		IR_A_LA_CARCEL(new String[] { "Marche Preso" }),

		/**
		 * Cuándo el casillero corresponde a un descanso (Salida, Parking, En la
		 * Cárcel de visita) String[descripción]
		 */
		DESCANSO(new String[] { "Descanso" }),

		/**
		 * Cuándo el casillero corresponde a Impuesto al Capital o lujo
		 * String[Descripción][Monto]
		 */
		IMPUESTO(new String[] { "Pargar Impuesto", "Monto" }),

		/**
		 * Cuándo el casillero corresponde a una propiedad comprada
		 * anteriormente. String[descripción]
		 */
		MI_PROPIEDAD(new String[] { "Mi propia propiedad" }),

		/*
		 * Cuándo el casillero corresponde a propiedad de otros jugador que se
		 * encuentra hipotecada. String[descripción]
		 */
		HIPOTECADA(new String[] { "Propiedad Hipotecada" });

		private String[] acciones;

		AccionEnCasillero(String[] acciones) {
			this.acciones = acciones;
		}

		public String[] getAcciones() {
			return acciones;
		}

		public void setAcciones(String[] acciones) {
			this.acciones = acciones;
		}
	}

	public final List<Jugador> turnos;

	public final EstadoJuego status;

	public final AccionEnCasillero accionCasillero;

	public final Banco banco;

	public final Tablero tablero;

	public Jugador currentPlayer;

	public final List<History> hirtoryList;
	
	public final Tarjeta tarjeta;

	public MonopolyGameStatus(List<Jugador> turnos, Banco banco,
			Tablero tablero, EstadoJuego status, AccionEnCasillero accion,
			Jugador currentPlayer, List<History> hirtoryList, Tarjeta tarjeta) {
		this.turnos = turnos;
		this.status = status;
		this.accionCasillero = accion;
		this.currentPlayer = currentPlayer;
		this.banco = banco;
		this.tablero = tablero;
		this.hirtoryList = hirtoryList;
		this.tarjeta = tarjeta;
	}

}
