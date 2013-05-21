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

	public CasilleroEstacion(int numeroCasillero,
			String nombreEstacion, TarjetaEstacion tarjetaEstacion) {
		super(numeroCasillero, Casillero.CASILLERO_ESTACION);
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
		return (TarjetaEstacion) tarjetaEstacion;
	}

}
