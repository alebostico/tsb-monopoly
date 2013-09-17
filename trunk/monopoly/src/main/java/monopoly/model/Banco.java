/*
 * 
 */
package monopoly.model;

import java.util.List;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Banco {

	public Banco() {
		GestorLogs.registrarLog("Banco Cargado");
	}

	// TODO: resolver como le cobra si no tiene plata

	/**
	 * resta del dinero del jugador el monto indicado
	 * 
	 * @param jugador
	 *            el jugador al que se quiere cobrar
	 * @param monto
	 *            el monto que se quiere cobrar
	 * @return true si puede cobrar el monto, false si no muede cobrar
	 */
	public boolean cobrar(Jugador jugador, int monto) {
		if (jugador.puedePagar(monto)) {
			jugador.pagar(monto);
			return true;
		} else
			return false;
	}

	/**
	 * suma al dinero del jugador el monto indicado
	 * 
	 * @param jugador
	 *            el jugador al que se debe pagar
	 * @param monto
	 *            el monto que se debe pagar
	 * @return true si se pudo pagar el monto
	 */
	public boolean pagar(Jugador jugador, int monto) {
		jugador.cobrar(monto);
		return true;
	}

	/**
	 * suma al dinero del jugador el monto indicado por cada jugador de la lista
	 * que no sea el resta al dinero del resto de los jugadores el monto
	 * indicado
	 * 
	 * @param jugadores
	 *            todos los jugadores
	 * @param jugador
	 *            el jugador al que se le pagara
	 * @param monto
	 *            el monto que se debe pagar por cada jugador
	 * @return true si se pudo pagar el monto por cada jugador
	 */
	public boolean cobrarATodosPagarAUno(List<Jugador> jugadores,
			Jugador jugador, int monto) {

		for (Jugador jugadorLista : jugadores) {
			if (!jugadorLista.equals(jugador)) {
				this.cobrar(jugadorLista, monto);
				this.pagar(jugador, monto);

			}
		}

		return true;
	}

	/**
	 * resta al dinero del jugador el monto indicado para las casas por el
	 * numero de casas mas el monto indicado para los hoteles por el numero de
	 * hoteles
	 * 
	 * @param jugador
	 *            el jugador al que se le cobrara
	 * @param montoPorCasa
	 *            el monto que se debe pagar por cada casa
	 * @param montoPorHotel
	 *            el monto que se debe paga por cada hotel
	 * @return true si se pudo pagar el monto total
	 */
	public boolean cobrarPorCasaYHotel(Jugador jugador, int montoPorCasa,
			int montoPorHotel) {
		int monto = jugador.getNroCasas() * montoPorCasa
				+ jugador.getNroHoteles() * montoPorHotel;
		return this.cobrar(jugador, monto);

	}

	/**
	 * toma una propiedad en hipotega y paga al jugador el valor indicado para
	 * la hipoteca de esta propiedad
	 * 
	 * @param jugador
	 *            el jugador que hipoteca la propiedad
	 * @param tarjetaPropiedad
	 *            la propiedad que hipoteca el jugador
	 * @return true so
	 */
	public boolean hipotecarPropiedad(Jugador jugador,
			TarjetaPropiedad tarjetaPropiedad) {
		if (this.pagar(jugador, tarjetaPropiedad.getValorHipotecario())) {
			tarjetaPropiedad.setHipotecada(true);
			return true;
		} else
			return false;
	}

	public boolean deshipotecarPropiedad(Jugador jugador,
			TarjetaPropiedad tarjetaPropiedad) {
		return false;
	}

	public boolean comprarEdificio(Jugador jugador, TarjetaCalle tarjetaCalle) {
		return false;
	}

	public boolean venderEdificio(Jugador jugador, TarjetaCalle tarjetaCalle) {
		return false;
	}

}
