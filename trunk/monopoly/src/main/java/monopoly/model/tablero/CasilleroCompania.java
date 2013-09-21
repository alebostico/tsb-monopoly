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

	public CasilleroCompania(int numeroCasillero, String nombreCompania, Tablero tablero,
			TarjetaCompania tarjetaCompania) {
		super(numeroCasillero, Casillero.CASILLERO_COMPANIA, tablero);
		this.nombreCompania = nombreCompania;
		this.tarjetaCompania = tarjetaCompania;
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public TarjetaCompania getTarjetaCompania() {
		return tarjetaCompania;
	}

	public TarjetaCompania comprarCompania(Jugador jugador) {
		// TODO: implementar el metodo que realiza la compra de la Compania.
		if(this.getTablero().getBanco().venderPropiedad(jugador, this.getTarjetaCompania()))
			return (TarjetaCompania) tarjetaCompania;
		return null;
	}

	public String toString() {
		return super.toString()
				+ "\n\tCasilleroCompania [nombreCompania="
				+ this.nombreCompania
				+ ", TarjetaCompania {\n"
				+ ((this.tarjetaCompania != null) ? this.tarjetaCompania
						.toString() : "<NO EXISTE LA TARJETA>") + "\n}]";
	}
}
