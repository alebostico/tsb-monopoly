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
	public static enum AccionEnCasillero {
		/**
		 * Cuando una propiedad es libre para ser comprada String[descripción]
		 */
		DISPONIBLE_PARA_VENDER(new String[] { "La propiedad %s está disponible para la venta." }),

		/**
		 * Cuando otro jugador compró la propiedad, entonces hay que pagar
		 * alquiler. String[descripción + monto + nombre jugador][monto a pagar]
		 */
		PAGAR_ALQUILER(new String[] {
				"Debes pagar %s € de alquiler al jugador %s.", "Monto" }),

		/**
		 * Cuando el casillero al que avanzó corresponde a una tarjeta de la
		 * suerte. String[descripción][Id Tarjeta de la Suerte]
		 */
		TARJETA_SUERTE(new String[] { "Sacaste una tarjeta de la suerte.", "IdTarjeta" }),

		/**
		 * Cuando el casillero al que avanzó corresponde a una tarjeta de
		 * comunidad. String[descripción][Id Tarjeta de Comunidad]
		 */
		TARJETA_COMUNIDAD(new String[] { "Sacaste una tarjeta comunidad.", "IdTarjeta" }),

		/**
		 * Cuando el casillero corresponde a Ir a la Cárcel. String[descripción]
		 */
		IR_A_LA_CARCEL(new String[] { "Debes ir a la cárcel." }),

		/**
		 * Cuándo el casillero corresponde a un descanso (Salida, Parking, En la
		 * Cárcel de visita) String[descripción]
		 */
		DESCANSO(new String[] { "Casillero %s, debes descansar." }),

		/**
		 * Cuándo el casillero corresponde a Impuesto de lujo
		 * String[Descripción][Monto]
		 */
		IMPUESTO_DE_LUJO(new String[] { "El banco te cobra 100 € por el impuesto de lujo.", "100" }),
		
		/**
		 * Cuándo el casillero corresponde a Impuesto sobre el capital.
		 * String[Descripción]
		 */
		IMPUESTO_SOBRE_CAPITAL(new String[] { "Impuesto sobre el capital. El banco te cobra 200 € o el 10% del valor total de su capital"}),

		/**
		 * Cuándo el casillero corresponde a una propiedad comprada
		 * anteriormente. String[descripción]
		 */
		MI_PROPIEDAD(new String[] { "Ya adquiriste esta propiedad." }),

		/*
		 * Cuándo el casillero corresponde a propiedad de otros jugador que se
		 * encuentra hipotecada. String[descripción]
		 */
		HIPOTECADA(new String[] { "Propiedad del jugador %s.", "La propiedad %s se encuentra hipotecada" });

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

	private List<Jugador> turnos;

	private EstadoJuego status;

	private AccionEnCasillero accionCasillero;

	private Banco banco;

	private Tablero tablero;

	private Jugador currentPlayer;

	private List<History> hirtoryList;
	
	private Tarjeta tarjeta;

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

	public List<Jugador> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Jugador> turnos) {
		this.turnos = turnos;
	}

	public EstadoJuego getStatus() {
		return status;
	}

	public void setStatus(EstadoJuego status) {
		this.status = status;
	}

	public AccionEnCasillero getAccionCasillero() {
		return accionCasillero;
	}

	public void setAccionCasillero(AccionEnCasillero accionCasillero) {
		this.accionCasillero = accionCasillero;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Jugador getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Jugador currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<History> getHirtoryList() {
		return hirtoryList;
	}

	public void setHirtoryList(List<History> hirtoryList) {
		this.hirtoryList = hirtoryList;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

}
