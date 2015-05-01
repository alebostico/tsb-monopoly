/**
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.List;

import monopoly.model.Estado.EstadoJuego;
import monopoly.model.tablero.Tablero;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class MonopolyGameStatus implements Serializable {

	private static final long serialVersionUID = -584315517388176762L;
	
	/**
	 * Estados
	 */
	
//	public static final int EMPEZAR = 0;	
//	public static final int TURNO_DADO = 1;
	
	/** Acciones posibles en un casillero **/
	public enum AccionEnCasillero {
		CASILLERO_DISPONIBLE(new String[] {"Casillero Disponible"}),
		PAGAR_ALQUILER(new String[] {"Casillero Ocupado","Monto a pagar"}),
		TARJETA_SUERTE(new String[] {"Tarjeta Suerte"}),
		TARJETA_COMUNIDAD(new String[] {"Tarjeta Comunidad"}),
		IR_A_LA_CARCEL(new String[] {"Marche Preso"}),
		DESCANSO(new String[] {"Descanso"}),
		IMPUESTO(new String[] {"Pargar Impuesto", "Monto"});
		
		private String[] acciones;
		
		AccionEnCasillero(String[] acciones){
			this.acciones = acciones;
		}

		public String[] getAcciones() {
			return acciones;
		}

		public void setAcciones(String[] acciones) {
			this.acciones = acciones;
		}
	}
	
	//public final 
	
	public final List<Jugador> turnos;
	
	public final EstadoJuego status;
	
	public final AccionEnCasillero accionCasillero;
	
	public final Banco banco;
	
	public final Tablero tablero;
	
	public Jugador currentPlayer;
	
	public final List<History> hirtoryList;
	
	public MonopolyGameStatus(List<Jugador> turnos, Banco banco, Tablero tablero, EstadoJuego status, AccionEnCasillero accion, Jugador currentPlayer, List<History> hirtoryList){
		this.turnos = turnos;
		this.status = status;
		this.accionCasillero = accion;
		this.currentPlayer = currentPlayer;
		this.banco = banco;
		this.tablero = tablero;
		this.hirtoryList = hirtoryList;
	}

}
