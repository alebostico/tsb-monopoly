package monopoly.model.tablero;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCompania;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroCompania extends Casillero {

	String nombreCompania;
	TarjetaCompania tarjetaCompania;

	public CasilleroCompania(int numeroCasillero,
			String nombreCompania, TarjetaCompania tarjetaCompania) {
		super(numeroCasillero, Casillero.CASILLERO_COMPANIA);
		this.nombreCompania = nombreCompania;
		this.tarjetaCompania = tarjetaCompania;
	}
	
	public String getNombreEstación() {
		return nombreCompania;
	}

	public TarjetaCompania getTarjetaCompania() {
		return tarjetaCompania;
	}

	public TarjetaCompania comprarCompania(Jugador jugador) {
		// TODO: implementar el metodo que realiza la compra de la Compania.
		return (TarjetaCompania) tarjetaCompania;
	}

}
