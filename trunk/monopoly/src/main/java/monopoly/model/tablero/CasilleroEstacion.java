package monopoly.model.tablero;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaEstacion;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroEstacion extends Casillero {

	String nombreEstacion;
	TarjetaEstacion tarjetaEstacion;

	public CasilleroEstacion(int numeroCasillero, String nombreEstacion, Tablero tablero,
			TarjetaEstacion tarjetaEstacion) {
		super(numeroCasillero, Casillero.CASILLERO_ESTACION, tablero);
		this.nombreEstacion = nombreEstacion;
		this.tarjetaEstacion = tarjetaEstacion;
	}

	public String getNombreEstacion() {
		return nombreEstacion;
	}

	public TarjetaEstacion getTarjetaEstacion() {
		return tarjetaEstacion;
	}

	public TarjetaEstacion comprarEstacion(Jugador jugador) {
		// TODO: implementar el metodo que realiza la compra de la estacion.
		if(this.getTablero().getBanco().venderPropiedad(jugador, this.getTarjetaEstacion()))
			return (TarjetaEstacion) tarjetaEstacion;
		return null;
	}

	public String toString() {
		return super.toString()
				+ "\n\tCasilleroEstacion [nombreEstacion="
				+ this.nombreEstacion
				+ ", TarjetaEstacion {\n"
				+ ((this.tarjetaEstacion != null) ? this.tarjetaEstacion
						.toString() : "<NO EXISTE LA TARJETA>") + "\n}]";
	}
}
