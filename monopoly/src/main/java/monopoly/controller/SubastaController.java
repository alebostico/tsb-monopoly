package monopoly.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaPropiedad;

public class SubastaController implements Serializable {

	private static final long serialVersionUID = 4337998511446349349L;

	private List<Jugador> jugadoresList;

	private Jugador[] indexList;

	private int indexActual;
	
	private TarjetaPropiedad propiedadSubastada;

	public SubastaController(TarjetaPropiedad pPropiedadSubastada) {
		this.jugadoresList = new ArrayList<Jugador>();
		this.propiedadSubastada = pPropiedadSubastada;
	}

	public void agregarJugadorASubasta(Jugador jugador) {
		jugadoresList.add(jugador);
	}

	public void inicializarVariables() throws Exception{
		indexList = new Jugador[jugadoresList.size()];
		for (int i = 0; i < jugadoresList.size(); i++) {
			indexList[i] = jugadoresList.get(i);
		}
		indexActual = 0;
	}
	
	public Jugador siguienteTurno() throws Exception{
		if(indexActual < indexList.length - 1)
			indexActual++;
		else
			indexActual = 0;
		return indexList[indexActual];
	}
	
	public Jugador jugadorActual() throws Exception
	{
		return indexList[indexActual];
	}
	
	public int cantidadJugadores(){
		return jugadoresList.size();
	}

}
