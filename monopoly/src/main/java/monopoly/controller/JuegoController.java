/**
 * 
 */
package monopoly.controller;

import java.util.TreeMap;

import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.Usuario;
import monopoly.util.list.CircularList;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@SuppressWarnings("unused")
public class JuegoController {
	
	private Juego juego;
	
	private int cantJugadores;
	
	private TarjetaController gestorTarjetas;
	
	private FichasController gestorFichas;
	
	private TreeMap<String, Jugador> jugadores;
	
	private CircularList<Jugador> turnosList;
	
	public JuegoController(Juego juego)
	{
		this.juego = juego;
		this.jugadores = new TreeMap<String, Jugador>();
		this.turnosList = new CircularList<Jugador>();
		gestorTarjetas = new TarjetaController(this.juego);
		gestorFichas = new FichasController();
	}
	
	/**
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}

	/**
	 * @param juego the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	/**
	 * @return the gestorTarjetas
	 */
	public TarjetaController getGestorTarjetas() {
		return gestorTarjetas;
	}

	/**
	 * @param gestorTarjetas the gestorTarjetas to set
	 */
	public void setGestorTarjetas(TarjetaController gestorTarjetas) {
		this.gestorTarjetas = gestorTarjetas;
	}

	/**
	 * @return the gestorFichas
	 */
	public FichasController getGestorFichas() {
		return gestorFichas;
	}

	/**
	 * @param gestorFichas the gestorFichas to set
	 */
	public void setGestorFichas(FichasController gestorFichas) {
		this.gestorFichas = gestorFichas;
	}
	
	/**
	 * 
	 */
	public void addPlayer(String senderID , Jugador jugador){
		if(!existePlayer(senderID)){
			jugadores.put(senderID, jugador);
			turnosList.push_back(jugador);
		}
		if(cantJugadores == jugadores.size()){
			// StartGame
		}
	}
	
	private boolean existePlayer(String senderID){
		return jugadores.containsKey(senderID);
	}

	/**
	 * @return the cantJugadores
	 */
	public int getCantJugadores() {
		return cantJugadores;
	}

	/**
	 * @param cantJugadores the cantJugadores to set
	 */
	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

}
