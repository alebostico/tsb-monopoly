package monopoly.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaPropiedad;

public class SubastaController implements Serializable {

	private static final long serialVersionUID = 4337998511446349349L;

	private List<Jugador> jugadoresList;
	
	private Jugador jugadorCreador;

	private Jugador[] indexList;

	private int indexActual;

	private TarjetaPropiedad propiedadSubastada;

	/**
	 * Constructor de la subasta.
	 * @param jugador Jugador que creó la subasta.
	 * @param pPropiedadSubastada Propiedad a subastar.
	 */
	public SubastaController(Jugador jugador, TarjetaPropiedad pPropiedadSubastada) {
		this.jugadorCreador = jugador;
		this.jugadoresList = new ArrayList<Jugador>();
		this.propiedadSubastada = pPropiedadSubastada;
	}

	public void agregarJugadorASubasta(Jugador jugador) {
		jugadoresList.add(jugador);
	}

	public void quitarJugadorDeSubasta(Jugador jugador) throws Exception {
		int index = 0;
		for (int i = 0; i < indexList.length; i++) {
			if (indexList[i].equals(jugador)) {
				index = i;
				break;
			}
		}
		jugadoresList.remove(indexList[index]);
		reordenarTurnos();		
	}

	public void inicializarVariables() throws Exception {
		indexList = new Jugador[jugadoresList.size()];
		for (int i = 0; i < jugadoresList.size(); i++) {
			indexList[i] = jugadoresList.get(i);
		}
		indexActual = 0;
	}
	
	public void reordenarTurnos() throws Exception
	{
		indexList = new Jugador[jugadoresList.size()];
		for (int i = 0; i < jugadoresList.size(); i++) {
			indexList[i] = jugadoresList.get(i);
		}
		if (indexActual > indexList.length - 1)
			indexActual = 0;
	}

	public Jugador siguienteTurno() throws Exception {
		if (indexActual < indexList.length - 1)
			indexActual++;
		else
			indexActual = 0;
		return indexList[indexActual];
	}

	public TarjetaPropiedad getPropiedadSubastada() {
		return propiedadSubastada;
	}

	public void setPropiedadSubastada(TarjetaPropiedad propiedadSubastada) {
		this.propiedadSubastada = propiedadSubastada;
	}

	public Jugador getJugadorActual() throws Exception {
		return indexList[indexActual];
	}

	public int cantidadJugadores() {
		return jugadoresList.size();
	}

	public List<Jugador> getJugadoresList() {
		return jugadoresList;
	}

	public Jugador getJugadorCreador() {
		return jugadorCreador;
	}

	public void setJugadorCreador(Jugador jugadorCreador) {
		this.jugadorCreador = jugadorCreador;
	}

}
