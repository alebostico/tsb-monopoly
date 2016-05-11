package monopoly.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaPropiedad;

public class SubastaController implements Serializable {

	private static final long serialVersionUID = 4337998511446349349L;

	private List<JugadorEnSubasta> jugadoresList;
	
	private Jugador jugadorCreador;

	private Jugador[] indexList;

	private int indexActual;

	private TarjetaPropiedad propiedadSubastada;
	
	private int ultimaPuja;

	/**
	 * Constructor de la subasta.
	 * @param jugador Jugador que creó la subasta.
	 * @param pPropiedadSubastada Propiedad a subastar.
	 */
	public SubastaController(Jugador jugador, TarjetaPropiedad pPropiedadSubastada) {
		this.jugadorCreador = jugador;
		this.jugadoresList = new ArrayList<JugadorEnSubasta>();
		this.propiedadSubastada = pPropiedadSubastada;
		this.ultimaPuja = 0;
	}

	public void agregarJugadorASubasta(Jugador jugador) {
		jugadoresList.add(new JugadorEnSubasta(jugador));
	}

	public void quitarJugadorDeSubasta(Jugador jugador) throws Exception {
		int index = 0;
		jugadoresList.get(indexActual).setPrimeraVez(false);
		for (int i = 0; i < jugadoresList.size(); i++) {
			if (jugadoresList.get(i).jugadorEnSubasta.equals(jugador)) {
				index = i;
				break;
			}
		}
		jugadoresList.remove(index);
		reordenarTurnos();		
	}

	public void inicializarVariables() throws Exception {
		indexList = new Jugador[jugadoresList.size()];
		for (int i = 0; i < jugadoresList.size(); i++) {
			indexList[i] = jugadoresList.get(i).getJugadorEnSubasta();
		}
		indexActual = 0;
	}
	
	public void reordenarTurnos() throws Exception
	{
		indexList = new Jugador[jugadoresList.size()];
		for (int i = 0; i < jugadoresList.size(); i++) {
			indexList[i] = jugadoresList.get(i).getJugadorEnSubasta();
		}
		if (indexActual > indexList.length - 1)
			indexActual = 0;
	}

	public Jugador siguienteTurno(int montoPuja) throws Exception {
		this.ultimaPuja = montoPuja;
		if (indexActual < indexList.length - 1){
			jugadoresList.get(indexActual).setPrimeraVez(false);
			indexActual++;
		}
		else
			indexActual = 0;
		return indexList[indexActual];
	}
	
	public boolean JugadorActualEsLaPrimeraVez(){
		return jugadoresList.get(indexActual).isPrimeraVez();
	}
	
	public boolean EsLaPrimeraVez(Jugador pJugador){
		int index = 0;
		for (int i = 0; i < indexList.length; i++) {
			if (indexList[i].equals(pJugador)) {
				index = i;
				break;
			}
		}
		return jugadoresList.get(index).isPrimeraVez();
	}
	
	public void marcarComoPostor(Jugador jugador) throws Exception {
		int index = 0;
		for (int i = 0; i < jugadoresList.size(); i++) {
			if (jugadoresList.get(i).jugadorEnSubasta.equals(jugador)) {
				index = i;
				break;
			}
		}
		jugadoresList.get(index).setPrimeraVez(false);
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
		List<Jugador> jugadores = new ArrayList<Jugador>();
		for (JugadorEnSubasta jugador : jugadoresList) {
			jugadores.add(jugador.getJugadorEnSubasta());
		}
		return jugadores;
	}

	public Jugador getJugadorCreador() {
		return jugadorCreador;
	}

	public void setJugadorCreador(Jugador jugadorCreador) {
		this.jugadorCreador = jugadorCreador;
	}
	
	public int getUltimaPuja() {
		return ultimaPuja;
	}

	public void setUltimaPuja(int ultimaPuja) {
		this.ultimaPuja = ultimaPuja;
	}

	public class JugadorEnSubasta {
		
		private Jugador jugadorEnSubasta;
		private boolean primeraVez;
		
		public JugadorEnSubasta(){
			primeraVez = true;
		}
		
		public JugadorEnSubasta(Jugador pJugador){
			jugadorEnSubasta = pJugador;
			primeraVez = true;
		}

		public Jugador getJugadorEnSubasta() {
			return jugadorEnSubasta;
		}

		public void setJugadorEnSubasta(Jugador jugadorEnSubasta) {
			this.jugadorEnSubasta = jugadorEnSubasta;
		}

		public boolean isPrimeraVez() {
			return primeraVez;
		}

		public void setPrimeraVez(boolean primeraVez) {
			this.primeraVez = primeraVez;
		}
		
	}

}


