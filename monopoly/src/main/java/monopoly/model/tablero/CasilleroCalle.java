package monopoly.model.tablero;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroCalle extends Casillero {

	String nombreCalle;
	TarjetaPropiedad tarjetaCalle;

	public CasilleroCalle(int numeroCasillero, String nombreCalle,
			TarjetaPropiedad tarjetaCalle) {
		super(numeroCasillero, Casillero.CASILLERO_CALLE);
		this.nombreCalle = nombreCalle;
		this.tarjetaCalle = tarjetaCalle;
	}

	public String getNombreCalle() {
		return nombreCalle;
	}

	public TarjetaPropiedad getTarjetaCalle() {
		return tarjetaCalle;
	}

	public TarjetaCalle comprarCalle(Jugador jugador) {
		// TODO: implementar el metodo que realiza la compra de la calle.
		return (TarjetaCalle) tarjetaCalle;
	}

	public String toString() {
		return super.toString()
				+ "\n\tCasilleroCalle [nombreCalle="
				+ this.nombreCalle
				+ ", TarjetaCalle {\n"
				+ ((this.tarjetaCalle != null) ? this.tarjetaCalle.toString()
						: "<NO EXISTE LA TARJETA>") + "\n}]";
	}

}
