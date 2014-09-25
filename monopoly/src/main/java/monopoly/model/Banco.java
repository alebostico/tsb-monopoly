/*
 * 
 */
package monopoly.model;

import java.util.List;

import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Banco {
	
	private List <TarjetaPropiedad> tarjPropiedadList;
	private int nroCasas;
	private int nroHoteles;

	public Banco() {
		
		//TODO cargar todas las propiedades
		//cargar las casas
		//cargar los hoteles
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
	 * @return the tarjPropiedadList
	 */
	public List<TarjetaPropiedad> getTarjPropiedadList() {
		return tarjPropiedadList;
	}

	/**
	 * @param tarjPropiedadList the tarjPropiedadList to set
	 */
	public void setTarjPropiedadList(List<TarjetaPropiedad> tarjPropiedadList) {
		this.tarjPropiedadList = tarjPropiedadList;
	}

	/**
	 * @return the nroCasas
	 */
	public int getNroCasas() {
		return nroCasas;
	}

	/**
	 * @param nroCasas the nroCasas to set
	 */
	public void setNroCasas(int nroCasas) {
		this.nroCasas = nroCasas;
	}

	/**
	 * @return the nroHoteles
	 */
	public int getNroHoteles() {
		return nroHoteles;
	}

	/**
	 * @param nroHoteles the nroHoteles to set
	 */
	public void setNroHoteles(int nroHoteles) {
		this.nroHoteles = nroHoteles;
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
	 * toma una propiedad en hipoteca y paga al jugador el valor indicado para
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

	/**
	 * deshipoteca una propiedad indicada y cobra al jugador
	 * la hipoteca de esta propiedad mas el 10 %
	 * 
	 * @param jugador
	 *            el jugador que deshipoteca la propiedad
	 * @param tarjetaPropiedad
	 *            la propiedad que deshipoteca el jugador
	 * @return true 
	 * 			  si el jugador puede pagar la hipoteca y lo hace. false si el jugador no puede pagar la hipoteca
	 */
	public boolean deshipotecarPropiedad(Jugador jugador,
			TarjetaPropiedad tarjetaPropiedad) {
		if(this.cobrar(jugador, (int)(tarjetaPropiedad.getValorHipotecario()*1.10)))
		{			
			tarjetaPropiedad.setHipotecada(false);
			return true;
		}
		else
			return false;
	}

	/**
	 * vende una propiedad al jugador 
	 * @param jugador
	 *            el jugador que compra la propiedad
	 * @param tarjetaPropiedad
	 *            la propiedad compra el jugador
	 * @return true 
	 * 			  si el jugador puede pagar la propiedad y la compra. false si el jugador no puede pagar la propiedad
	 */
	public boolean venderPropiedad(Jugador jugador, TarjetaPropiedad tarjetaPropiedad)
	{
		if(this.cobrar(jugador, tarjetaPropiedad.getValorPropiedad()))
		{
			tarjPropiedadList.remove(tarjetaPropiedad);
			jugador.getTarjPropiedadList().add(tarjetaPropiedad);
			return true;
		}
		return false;
	}

}
