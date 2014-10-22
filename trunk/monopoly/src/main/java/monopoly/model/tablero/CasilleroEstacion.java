package monopoly.model.tablero;

import java.io.Serializable;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaEstacion;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CasilleroEstacion extends Casillero implements Serializable{

	private static final long serialVersionUID = 5105639502812316240L;

	private String nombreEstacion;
	private TarjetaEstacion tarjetaEstacion;

	public CasilleroEstacion(int numeroCasillero, String nombreEstacion, Tablero tablero,
			TarjetaEstacion tarjetaEstacion) {
		super(numeroCasillero, TipoCasillero.C_ESTACION, tablero);
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Casillero Estación [nombre estación: " + this.nombreEstacion + ", ");
		sb.append((this.tarjetaEstacion != null) ? this.tarjetaEstacion.toString()
				: "<NO EXISTE LA TARJETA>");
		sb.append("] }");
		
		return sb.toString();
	}
}
